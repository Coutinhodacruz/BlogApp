package com.example.blog.service;

import com.example.blog.dto.request.LoginRequest;
import com.example.blog.dto.request.RegistrationRequest;
import com.example.blog.dto.request.UpdateRequest;
import com.example.blog.dto.request.UpdateUserRequest;
import com.example.blog.dto.response.LoginResponse;
import com.example.blog.dto.response.RegistrationResponse;
import com.example.blog.dto.response.UpdateResponse;

public interface UserService {


    RegistrationResponse register(RegistrationRequest registrationRequest);

    LoginResponse login(LoginRequest loginRequest);

    UpdateResponse updateProfile(UpdateUserRequest updateRequest, Long id);
}
