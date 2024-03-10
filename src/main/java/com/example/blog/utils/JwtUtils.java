package com.example.blog.utils;

import java.time.Instant;
import java.util.Date;
import com.auth0.jwt.algorithms.Algorithm;

import com.auth0.jwt.JWT;

public class JwtUtils {


    public static String generateAccessToken(Long id){
        return JWT.create()
                .withClaim("User_id", id)
                .withIssuer("blog_app")
                .withExpiresAt(Date.from(Instant.now().plusSeconds(2592000)))
                .sign(Algorithm.HMAC512("blog_secret"));

    }
}