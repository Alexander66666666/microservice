package com.example.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO для регистрации пользователя
 */
@Data
public class RegisterRequest {
    @NotBlank(message = "Имя пользователя не может быть пустым")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;
}
