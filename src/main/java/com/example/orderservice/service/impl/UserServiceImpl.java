package com.example.orderservice.service.impl;

import com.example.orderservice.constant.Role;
import com.example.orderservice.entity.User;
import com.example.orderservice.exception.UsernameAlreadyExistsException;
import com.example.orderservice.repository.UserRepository;
import com.example.orderservice.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реальзиация сервиса для управления пользователями
 * Обеспечивает создание, поиск и удаление пользователей, а также проверку уникальности имен.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создает нового пользователя в системе.
     * Проверяет уникальность имени пользователя, шифрует пароль перед сохранением
     *
     * @param username имя пользователя(логин)
     * @param password пароль в открытом виде
     * @param role роль пользователя в системе
     * @return созданный пользователь
     * @throws UsernameAlreadyExistsException если пользователь с таким именем уже существует
     */
    @Transactional
    @Override
    public User createUser(String username, String password, Role role) {
        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException("Username already exists" + username);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }

    /**
     * Находит пользователя по логину
     *
     * @param username имя пользователя для поиска
     * @return Optional с пользователем, если найден,  иначе пустой Optional
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Получает список всех пользователей в системе
     *
     * @return список всех пользователей
     */
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     */
    @Override
    @Transactional
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    /**
     * Проверяет, существует ли пользователь с указанным именем
     *
     * @param username имя пользователя для проверки
     * @return если пользователь с таким именем существует, иначе false
     */
    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}
