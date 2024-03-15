package com.example.blog.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@Builder
public class GetUserResponse {

    private Long id;
    private String username;

    private String email;

    private String profileImage;
}
