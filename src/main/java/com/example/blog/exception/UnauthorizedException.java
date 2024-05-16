package com.example.blog.exception;

public class UnauthorizedException extends BlogAppBaseException {
    public UnauthorizedException(String message) {
        super(message);
    }
}
