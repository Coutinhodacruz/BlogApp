package com.example.blog.service;

import com.example.blog.dto.request.LoginRequest;
import com.example.blog.dto.request.RegistrationRequest;
import com.example.blog.dto.request.UpdateUserRequest;
import com.example.blog.dto.response.LoginResponse;
import com.example.blog.dto.response.RegistrationResponse;
import com.example.blog.model.User;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {


    RegistrationResponse register(RegistrationRequest registrationRequest);

    LoginResponse login(LoginRequest loginRequest);

//    UpdateResponse updateProfile(UpdateUserRequest updateRequest, HttpServletRequest servletRequest) throws JsonPatchException;

    User updateUser(UpdateUserRequest updateRequest, Long id);

    void logOut(HttpServletResponse response);

    void deleteUser(Long userId, User currentUser);

    List<User> getUsers(User currentUser, int startIndex, int limit, String sortDirection);

//    GetUserResponse getUserById(Long id);
}
