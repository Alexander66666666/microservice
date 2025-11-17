package com.example.orderservice.service.interfaces;

import com.example.orderservice.dto.request.LoginRequest;
import com.example.orderservice.dto.request.RegisterRequest;
import com.example.orderservice.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest request);
    void register(RegisterRequest request);
}
