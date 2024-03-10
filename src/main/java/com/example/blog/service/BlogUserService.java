package com.example.blog.service;


import com.example.blog.config.MailConfig;
import com.example.blog.dto.request.JavaMailerRequest;
import com.example.blog.dto.request.LoginRequest;
import com.example.blog.dto.request.RegistrationRequest;
import com.example.blog.dto.request.UpdateRequest;
import com.example.blog.dto.request.UpdateUserRequest;
import com.example.blog.dto.response.LoginResponse;
import com.example.blog.dto.response.RegistrationResponse;
import com.example.blog.dto.response.UpdateResponse;
import com.example.blog.exception.BlogAppBaseException;
import com.example.blog.exception.UserAlreadyExistException;
import com.example.blog.exception.UserLoginWithInvalidCredentialsException;
import com.example.blog.exception.UserNotFoundException;
import com.example.blog.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.example.blog.repositories.UserRepository;
import com.example.blog.utils.JwtUtils;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.blog.dto.response.ResponseMessage.*;
import static com.example.blog.exception.ExceptionMessage.*;

@Service
@AllArgsConstructor
@Slf4j
public class BlogUserService implements UserService{


    private final UserRepository userRepository;

//    private final PasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final MailConfig mailConfig;

    private final MailService mailService;

    private final CloudService cloudService;


    @Override
    public RegistrationResponse register(RegistrationRequest registrationRequest) {
        String email = registrationRequest.getEmail().toLowerCase().trim();
        String password = registrationRequest.getPassword();
//        String passwordHash = passwordEncoder.encode(password);
        String username = registrationRequest.getUsername();
        if(userAlreadyExist(email)) throw new UserAlreadyExistException(registrationRequest.getEmail() + " " + USER_ALREADY_EXIST_EXCEPTION.getMessage());

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setIsAdmin(false);
        String token = tokenService.createToken(email);

        JavaMailerRequest javaMailerRequest = new JavaMailerRequest();
        javaMailerRequest.setTo(email);
        javaMailerRequest.setMessage(HERE_BELOW_IS_YOUR_TOKEN.name() + " " + token);
        javaMailerRequest.setSubject(VERIFICATION_OTP.name());
        javaMailerRequest.setFrom(mailConfig.getFromEmail());

        sendToken(javaMailerRequest);


        User savedUser = userRepository.save(user);

        RegistrationResponse response = new RegistrationResponse();
        response.setId(savedUser.getId());
        response.setMessage(USER_REGISTRATION_SUCCESSFUL.name());
        return response;
    }

    private void sendToken(JavaMailerRequest javaMailerRequest) {
        mailService.send(javaMailerRequest);
    }


    private boolean userAlreadyExist(String email) {
        return userRepository.findByEmail(email).isPresent();
    }


    @Override
    public LoginResponse login(LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        log.info("User name -->{}", email);
        log.info("User password -->{}", password);
        return verifyLoginDetails(email, password);
    }


    private LoginResponse verifyLoginDetails(String email, String password) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                return loginResponseMapper(user);
            } else {
                throw new UserLoginWithInvalidCredentialsException(INVALID_CREDENTIALS_EXCEPTION.getMessage());
            }
        } else {

            throw new UserLoginWithInvalidCredentialsException(USER_WITH_EMAIL_NOT_FOUND_EXCEPTION.getMessage());
        }
    }


    private static LoginResponse loginResponseMapper(User user) {
        LoginResponse loginResponse = new LoginResponse();
        String accessToken = JwtUtils.generateAccessToken(user.getId());
        BeanUtils.copyProperties(user, loginResponse);
        loginResponse.setJwtToken(accessToken);
        log.info("token --> {}", accessToken);
        loginResponse.setMessage("Login Successful");
        return loginResponse;
    }

    @Override
    public UpdateResponse updateProfile(UpdateUserRequest updateUserRequest, Long id) {
        ModelMapper modelMapper = new ModelMapper();

        String url = uploadImage(updateUserRequest.getProfilePicture());
        User user = findUserById(id);
        JsonPatch updatePatch = buildUpdatePatch(updateUserRequest);
        return applyPatch(updatePatch, user);
    }

    private JsonPatch buildUpdatePatch(UpdateUserRequest updateUserRequest) {
        Field[] fields = updateUserRequest.getClass().getDeclaredFields();
        List<ReplaceOperation> operations = Arrays.stream(fields)
                .filter(field ->  validateField(updateUserRequest, field))
                .map(field->buildReplaceOperation(updateUserRequest, field))
                .toList();
        List<JsonPatchOperation> patchOperations = new ArrayList<>(operations);
        return new JsonPatch(patchOperations);
    }

    private static boolean validateField(UpdateUserRequest updateUserRequest, Field field) {
        List<String> list = List.of("username", "email", "password", "country", "profileImage");
        field.setAccessible(true);
        try {
            return field.get(updateUserRequest)!=null &&!list.contains(field.getName());
        } catch (IllegalAccessException e) {
            throw new BlogAppBaseException(e.getMessage());
        }
    }

    private static ReplaceOperation buildReplaceOperation(UpdateUserRequest updateUserRequest, Field field) {
        field.setAccessible(true);
        try {
            String path = JSON_PATCH_PATH_PREFIX+field.getName();
            JsonPointer pointer = new JsonPointer(path);
            var value = field.get(updateUserRequest);
            TextNode node = new TextNode(value.toString());
            return new ReplaceOperation(pointer, node);
        } catch (Exception exception) {
            throw new BlogAppBaseException(exception.getMessage());
        }
    }

    private UpdateResponse applyPatch(JsonPatch updatePatch, User user) throws JsonPatchException {
        ObjectMapper objectMapper = new ObjectMapper();
        //1. Convert user to JsonNode
        JsonNode userNode = objectMapper.convertValue(user, JsonNode.class);

        //2. Apply patch to JsonNode from step 1
        JsonNode updatedNode = updatePatch.apply(userNode);
        //3. Convert updatedNode to user
        user = objectMapper.convertValue(updatedNode, User.class);
        log.info("user-->{}", user);
        //4. Save updatedUser from step 3 in the DB
        var savedUser=userRepository.save(user);
        log.info("user-->{}", savedUser);
        return new UpdateResponse(PROFILE_UPDATE_SUCCESSFUL.name());

    }

    public User findUserById(Long id){
        Optional<User> foundUser = userRepository.findById(id);
        User user = foundUser.orElseThrow(()->new UserNotFoundException(USER_NOT_FOUND_EXCEPTION.getMessage()));
        return user;
    }

    private String uploadImage(MultipartFile profileImage) {
        boolean isFormWithProfileImage = profileImage !=null;
        if (isFormWithProfileImage) return cloudService.upload(profileImage);
        throw new RuntimeException("image upload failed");
    }
}
