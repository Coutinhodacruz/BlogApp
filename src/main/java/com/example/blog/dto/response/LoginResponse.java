package com.example.blog.dto.response;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResponse {

    private String message;
    private String jwtToken;
}
