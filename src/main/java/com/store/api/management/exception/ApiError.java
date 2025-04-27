package com.store.api.management.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiError {
    private String status;
    private String errorCode;
    private String path;
    private String message;
    private List<String> errors;
    private long timestamp;
}
