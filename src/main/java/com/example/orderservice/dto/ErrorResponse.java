package com.example.orderservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
/**
 * Стандартный формат ответа об ошибке в API.
 */
@Data
public class ErrorResponse {
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
