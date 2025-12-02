package com.example.orderservice.security;

import com.example.orderservice.entity.User;
import com.example.orderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация UserDetailsService для Spring Security.
 * Загружает данных пользователя из базы данных по имени пользователя для аутентификации.
 *
 * <p>Используется Spring Security в процессе аутентификации для получения информации о пользователе</p>
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Загружает пользователя по логину
     * Выполняет поиск в базе данных и преобразует сущность в UserDetails.
     *
     * @param username имя пользователя для поиска
     * @return объект UserDetails с данными пользователя
     * @throws UsernameNotFoundException если пользователь с указанным именем не найден
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
        return UserDetailsImpl.build(user);
    }
}
