package com.example.blog.dto.request;


import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class UpdateUserRequest {

    private Long id;

    private String username;


    private String email;

    private String password;

    private MultipartFile profilePicture;

    private LocalDateTime updatedAt;

    @PrePersist
    public void setCreatedAt() {
        this.updatedAt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        updatedAt.format(formatter);
    }

}
