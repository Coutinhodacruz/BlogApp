package com.example.blog.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class UpdateUserRequest {

    private Long id;

    private String username;


    private String email;

    private String password;

    private MultipartFile profilePicture;
}
