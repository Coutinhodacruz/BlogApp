package com.example.blog.dto.request;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserRequest {


    private String username;


    private String email;

    private String password;

    private String profilePicture;
}
