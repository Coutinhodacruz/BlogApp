package com.example.blog.service;

import com.example.blog.dto.request.LoginRequest;
import com.example.blog.dto.request.RegistrationRequest;
import com.example.blog.dto.request.UpdateUserRequest;
import com.example.blog.dto.response.LoginResponse;
import com.example.blog.dto.response.RegistrationResponse;
import com.example.blog.exception.BlogAppBaseException;
import com.example.blog.exception.UserAlreadyExistException;
import com.example.blog.model.User;
import com.example.blog.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
public class BlogUserServiceTest {


    @Autowired
    BlogUserService blogUserService;

    RegistrationRequest  request;

    private static Long labTestUserId;

    @BeforeEach
    void setUp(){
        request = new RegistrationRequest();
    }


    @Test
    @Order(1)
    public void testUserCanRegister(){
        request.setEmail("dominicrotimi@gmail.com");
        request.setUsername("Coutinho");
        request.setPassword("dacruz1234");
        request.setProfilePicture(getTestImage());
        RegistrationResponse response = blogUserService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
    }

    @Test
    @Order(1)
    public void testCanRegisterAnotherUser(){
        request.setEmail("teresejosephyisa@gmail.com");
        request.setUsername("joseph");
        request.setPassword("dacruz1234");
        request.setProfilePicture(getTestImage());
        RegistrationResponse response = blogUserService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
    }
    @Test
    @Order(2)
    public void testYouCanRegisterAnotherUser(){
        request.setEmail("coutinhodacruz10@gmail.com");
        request.setUsername("Bobby");
        request.setPassword("password");
        request.setProfilePicture(getTestImage());
        RegistrationResponse response = blogUserService.register(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
    }

    @Test
    @Order(3)
    public void testUserAlreadyExist(){

        try {
            
            request.setEmail("dominicrotimi@gmail.com");
            request.setUsername("Coutinho");
            request.setPassword("dacruz1234");
            request.setProfilePicture(getTestImage());

            RegistrationResponse response = blogUserService.register(request);
            assertThat(response).isNotNull();
            assertThat(response.getMessage()).isNotNull();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        assertThrows(UserAlreadyExistException.class, () -> blogUserService.register(request));

    }

    @Test
    @Order(4)
    public void testUSerCanLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("songujack@gmail.com");
        loginRequest.setPassword("dacruz1234");

        LoginResponse loginResponse = blogUserService.login(loginRequest);
        assertThat(loginResponse).isNotNull();
        assertThat(loginResponse.getMessage()).isNotNull();
    }


//    @Test
//    @Order(5)
//    public void tesThatUserCanUpdateAccount() throws JsonPatchException {
//        HttpServletRequest request = buildHttpServletRequestForToken();
//        UpdateUserRequest updateUserRequest = buildUpdateRequest();
//        UpdateResponse response = blogUserService.updateProfile(updateUserRequest, request);
//        assertThat(response).isNotNull();
//        User userResponse = blogUserService.findUserById(500L);
//        String fullName = userResponse.getUsername();
//        String expectedFullName = updateUserRequest.getUsername() +
//                " ";
//
//        assertThat(fullName).isEqualTo(expectedFullName);
//
//    }

    @Test
    public void updateStudentTest() {

        request.setEmail("abobby@gmail.com");
        request.setUsername("Dacruz");
        request.setPassword("12345");
        request.setProfilePicture(getTestImage());
        var registerResponse = blogUserService.register(request);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(registerResponse.getId());
        updateUserRequest.setUsername("Arnold");
        updateUserRequest.setEmail("amaka@gmail.com");
        updateUserRequest.setPassword("password");
        MultipartFile testImage = getUpdatedImage();
        updateUserRequest.setProfilePicture(testImage);

        User updatedUser = blogUserService.updateUser(updateUserRequest, registerResponse.getId());
        assertNotNull(updatedUser);

        assertEquals(updateUserRequest.getUsername(), "Arnold");
        assertEquals(updatedUser.getEmail(), "amaka@gmail.com");

    }




    private UpdateUserRequest buildUpdateRequest() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setId(500L);
        updateUserRequest.setUsername("Arnold");
        updateUserRequest.setEmail("amaka@gmail.com");
        updateUserRequest.setPassword("password");
        MultipartFile testImage = getUpdatedImage();
        updateUserRequest.setProfilePicture(testImage);


        return updateUserRequest;
    }


    private MultipartFile getTestImage(){
        // Obtain a path that points to test image
        Path path = Paths.get("C:\\Users\\Admin\\blog\\src\\test\\resources\\image\\IMG.jpg");
        // Create stream that can read from file pointed to by path
        try(InputStream inputStream = Files.newInputStream(path)){
            // Create a MultiPathFile using bytes from file pointed to b path
            MultipartFile image = new MockMultipartFile("test_image", inputStream);
            return image;
        }catch (Exception exception){
            throw new BlogAppBaseException(exception.getMessage());
        }
    }

    private MultipartFile getUpdatedImage(){
        // Obtain a path that points to test image
        Path path = Paths.get("C:\\Users\\Admin\\blog\\src\\test\\resources\\image\\IMG-20230824-WA0003.jpg");
        // Create stream that can read from file pointed to by path
        try(InputStream inputStream = Files.newInputStream(path)){
            // Create a MultiPathFile using bytes from file pointed to b path
            return new MockMultipartFile("test_image", inputStream);
        }catch (Exception exception){
            throw new BlogAppBaseException(exception.getMessage());
        }
    }



    private static HttpServletRequest buildHttpServletRequestForToken() {
        Long userId = labTestUserId;
        String token = JwtUtils.generateAccessToken(userId);

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn("Bearer " + token);
        return request;
    }


}






//    @Test
//    public void updateStudentTest() {
//        RegistrationRequest registerRequest = new RegistrationRequest();
//        registerRequest.setUsername("Bobby");
//        registerRequest.setEmail("coutinhodacruz@gmail.com");
//        registerRequest.setPassword("password");
//        var registerResponse = blogUserService.register(registerRequest);
//
//        UpdateUserRequest updateRequest = new UpdateUserRequest();
//        updateRequest.setId(registerResponse.getId());
//        updateRequest.setUsername("Arnold");
//        updateRequest.setEmail("bobby@gmail.com");
//        MultipartFile testImage = getTestImage();
//        updateRequest.setProfilePicture(testImage);
//        updateRequest.setPassword("12345");
//        User updatedStudent = blogUserService.updateUser(updateRequest, registerResponse.getId());
//        assertNotNull(updatedStudent);
//
//        assertEquals(updateRequest.getUsername(), "Arnold");
//
//    }
