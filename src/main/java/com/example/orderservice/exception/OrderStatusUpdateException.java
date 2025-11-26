package com.example.orderservice.exception;

public class OrderStatusUpdateException extends RuntimeException {
    public OrderStatusUpdateException(String message){
        super(message);
    }
}
