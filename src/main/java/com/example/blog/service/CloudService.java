package com.example.blog.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudService {

    String upload(MultipartFile file);
}