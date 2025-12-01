package com.example.orderservice.exception;

import com.example.orderservice.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse eror = new ErrorResponse("USER_NOT_FOUND", "Пользователь не найден");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(eror);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse eror = new ErrorResponse("ORDER_NOT_FOUND", "Заказ не найден ");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(eror);
    }

    @ExceptionHandler(OrderStatusUpdateException.class)
    public ResponseEntity<ErrorResponse> handleOrderStatusNotFoundException(OrderStatusUpdateException ex) {
        ErrorResponse eror = new ErrorResponse
                ("ORDER_STATUS_UPDATE_ERROR", "Ошибка обновления статуса заказа");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eror);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse eror = new ErrorResponse("INVALID_INPUT", "Недопустимый вход");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eror);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericExсeption(Exception ex) {
        ErrorResponse eror = new ErrorResponse
                ("INTERNAL_SERVER_ERROR", "Внутренняя ошибка сервера");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(eror);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        ErrorResponse eror = new ErrorResponse
                ("USERNAME_ALREADY_EXISTS", "Имя пользователя уже существует");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(eror);
    }
}
