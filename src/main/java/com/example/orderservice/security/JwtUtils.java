package com.example.orderservice.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * Утилита для работы с JWT токенами.
 */
@Slf4j
@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    /**
     * Создает ключ для подписи JWT.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());

    }

    /**
     * Генерирует JWT токен для аутентифицированного пользователя
     * Извлекает имя пользователя из объекта UserDetails и создает на его основе токен.
     * @param userPrincipal объект с данными аутентифицированного пользователя
     * @return сгенерированный JWT токен
     */
    public String generateJwtToken(UserDetails userPrincipal) {
        return generateTokenFromUsername(userPrincipal.getUsername());
    }

    /**
     * Генерирует JWT токен по имени пользователя
     * Создает токен с указанным именем пользователя в качестве субъекта,
     * временем выдачи (issuedAt) и временем истечения (expiration).
     *
     * @param username имя пользователя, которое будет включено в токен как subject
     * @return сгенерированный JWT токен в формате строки
     */
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
