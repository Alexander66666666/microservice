package com.example.orderservice.service.interfaces;

import com.example.orderservice.constant.Role;
import com.example.orderservice.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User createUser(String username, String password, Role role);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void deleteUser(UUID id);

    boolean existsByUsername(String username);
}
