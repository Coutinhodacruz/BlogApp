package com.example.blog.exception;

public class UserLoginWithInvalidCredentialsException extends RuntimeException {



    public UserLoginWithInvalidCredentialsException(String message) {
        super(message);
    }
}
