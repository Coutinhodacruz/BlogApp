package com.example.blog.service;

import com.example.blog.model.Token;

public interface TokenService {

    String createToken(String email);

    Token findByOwnerEmail(String email);

    void deleteToken(Long id);
}
