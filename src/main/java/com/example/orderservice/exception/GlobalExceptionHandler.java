package com.example.orderservice.exception;

import com.example.orderservice.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        ErrorResponse eror = new ErrorResponse("Пользователь не найден", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(eror);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerOrderNotFoundException(OrderNotFoundException ex) {
        ErrorResponse eror = new ErrorResponse("Заказ не найден", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(eror);
    }

    @ExceptionHandler(OrderStatusUpdateException.class)
    public ResponseEntity<ErrorResponse> handlerOrderStatusNotFoundException(OrderStatusUpdateException ex) {
        ErrorResponse eror = new ErrorResponse("Ошибка обновления статуса заказа", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(eror);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse eror = new ErrorResponse("Недопустимый вход", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(eror);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericExeption(Exception ex) {
        ErrorResponse eror = new ErrorResponse("Внутренняя ошибка сервера", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(eror);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        ErrorResponse eror = new ErrorResponse("Имя пользователя уже существует", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(eror);
    }
}
