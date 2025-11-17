package com.example.orderservice.dto.response;

import com.example.orderservice.entity.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OrderResponse {
    private UUID id;
    private String description;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private String username;
}
