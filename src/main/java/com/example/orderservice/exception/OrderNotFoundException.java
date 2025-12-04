package com.example.orderservice.exception;

/** Исключение при отсутствии заказа. */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message){
        super(message);
    }
}
