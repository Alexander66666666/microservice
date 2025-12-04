package com.example.orderservice.service.impl;

import com.example.orderservice.dto.request.LoginRequest;
import com.example.orderservice.dto.request.RegisterRequest;
import com.example.orderservice.dto.response.JwtResponse;
import com.example.orderservice.constant.Role;
import com.example.orderservice.security.JwtUtils;
import com.example.orderservice.security.UserDetailsImpl;
import com.example.orderservice.service.interfaces.AuthService;
import com.example.orderservice.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализация сервиса аутентификации и авторизации.
 * Обеспечивает процессы входа в систему и регистрации новых пользователей
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    /**
     * Выполняет аутентификацию пользователя и возвращает JWT токен.
     * @param request DTO с учетными данными пользователя (логин и пароль)
     * @return JwtResponse с JWT токеном, именем пользователя и его ролью
     * @throws RuntimeException в случае ошибки аутентификации
     */

    @Override
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateJwtToken(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getUsername(), roles.get(0));


    }

    /**
     * Регистрирует нового пользователя в системе.
     * Проверяет уникальность имени пользователя и создает новую учетную запись.
     *
     * @param request DTO с данными для регистрации (имя пользователя и пароль)
     * @throws RuntimeException если имя пользователя уже занято
     */
    @Override
    public void register(RegisterRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        userService.createUser(request.getUsername(), request.getPassword(), Role.USER);
    }
}


