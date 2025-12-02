package com.example.orderservice.dto.response;

import com.example.orderservice.constant.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO для ответа с информацией о заказе.
 * Используется для отправки данных заказа клиенту через API.
 */
@Data
public class OrderResponse {
    private UUID id;
    private String description;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String username;
}
