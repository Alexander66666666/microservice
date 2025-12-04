package com.example.orderservice.dto.response;

import com.example.orderservice.constant.Role;
import lombok.Data;

import java.util.UUID;
/**
 * DTO для ответа с информацией о пользователе.
 * Содержит основные данные пользователя для отображения клиенту.
 */
@Data
public class UserResponse {
    private UUID id;
    private String username;
    private Role role;
}
