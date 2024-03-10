package com.example.blog.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationRequest {

    private String email;

    private String username;

    private String password;


}
