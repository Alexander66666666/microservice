package com.example.orderservice.dto.response;

import lombok.Data;

/**
 * DTO для ответа с данными JWT токена после успешной аутентификации.
 * Содержит токен доступа, тип токена, имя пользователя и его роль
 */
@Data
public class JwtResponse {
    private String token;
    private String type = "bearer";
    private String username;
    private String role;

    public JwtResponse(String token, String username, String role){
        this.token = token;
        this.username = username;
        this.role = role;
    }
}
