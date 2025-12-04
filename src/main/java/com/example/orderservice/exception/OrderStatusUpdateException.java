package com.example.orderservice.exception;

/** Исключение при ошибке обновления статуса заказа. */
public class OrderStatusUpdateException extends RuntimeException {
    public OrderStatusUpdateException(String message){
        super(message);
    }
}
