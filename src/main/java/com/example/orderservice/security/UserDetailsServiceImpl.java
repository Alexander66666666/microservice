package com.example.orderservice.security;

import com.example.orderservice.entity.User;
import com.example.orderservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    // метод, который загружает пользователя по имени
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Ищет пользователя в базе данных по логину для получения данных пользователя из БД
        User user = userRepository.findByUsername(username)
                //если пользователь не найден, бросает исключение
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));
// Преобразует нашего User в UserDetailsImpl и возвращает его.
        return UserDetailsImpl.build(user);
    }
}
