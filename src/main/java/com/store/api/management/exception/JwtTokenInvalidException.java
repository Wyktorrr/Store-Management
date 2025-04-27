package com.store.api.management.exception;

public class JwtTokenInvalidException extends RuntimeException{
    public JwtTokenInvalidException(String message) {
        super(message);
    }
}
