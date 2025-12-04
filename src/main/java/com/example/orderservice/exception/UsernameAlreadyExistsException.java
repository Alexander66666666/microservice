package com.example.orderservice.exception;

/** Исключение при попытке регистрации существующего имени пользователя. */
public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String message){
        super(message);
    }
}
