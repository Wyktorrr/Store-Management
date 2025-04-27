package com.store.api.management.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ EntityNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.name())
                .errorCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .errors(new ArrayList<>())
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .errors(new ArrayList<>())
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleConflict(ConflictException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.CONFLICT.name())
                .errorCode(String.valueOf(HttpStatus.CONFLICT.value()))
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .errors(new ArrayList<>())
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.name())
                .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .errors(Collections.singletonList("Invalid credentials provided."))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenMissingException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleJwtTokenMissing(JwtTokenMissingException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.name())
                .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .errors(Collections.singletonList("JWT token is missing."))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtTokenInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleJwtTokenInvalid(JwtTokenInvalidException ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.UNAUTHORIZED.name())
                .errorCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()))
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .errors(Collections.singletonList("JWT token is invalid."))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(error.getField() + ": " + error.getDefaultMessage())
        );

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.name())
                .errorCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .path(request.getDescription(false))
                .message("Validation failed")
                .errors(errors)
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errorCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .path(request.getDescription(false))
                .message(ex.getMessage())
                .errors(Arrays.asList("An unexpected error occurred."))
                .timestamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
