package com.example.blog.service;

import com.example.blog.dto.request.LoginRequest;
import com.example.blog.dto.request.RegistrationRequest;
import com.example.blog.dto.request.UpdateUserRequest;
import com.example.blog.dto.response.GetUserResponse;
import com.example.blog.dto.response.LoginResponse;
import com.example.blog.dto.response.RegistrationResponse;
import com.example.blog.dto.response.UpdateResponse;
import com.example.blog.model.User;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {


    RegistrationResponse register(RegistrationRequest registrationRequest);

    LoginResponse login(LoginRequest loginRequest);

    UpdateResponse updateProfile(UpdateUserRequest updateRequest, HttpServletRequest servletRequest) throws JsonPatchException;

//    User updateUser(UpdateUserRequest updateRequest, Long id);

//    GetUserResponse getUserById(Long id);
}
