package com.example.blog.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class RegistrationRequest {

    private String email;

    private String username;

    private String password;

    private MultipartFile profilePicture;


}
