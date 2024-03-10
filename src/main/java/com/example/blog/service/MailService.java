package com.example.blog.service;

import com.example.blog.dto.request.JavaMailerRequest;

public interface MailService {


    void send (JavaMailerRequest javaMailerRequest);
}
