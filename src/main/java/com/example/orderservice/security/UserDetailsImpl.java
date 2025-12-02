package com.example.orderservice.security;

import com.example.orderservice.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Реализация интерфейса UserDetails Spring Security.
 * Адаптирует сущность для работы с механизмами безопасности Spring.
 *
 * <p>Содержит основные данные пользователя, необходимые для аутентификации и авторизации
 * </p>
 */
@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private UUID id;
    private String username;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Создает объект UserDetailsImpl на основе сущности User.
     * Преобразует роль пользователя в GrantedAuthority с префиксом "ROLE_".
     *
     * @param user сущность пользователя из базы данных
     * @return объект UserDetailsImpl для Spring Security
     */
    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = List.of
                (new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    /**
     * Указывает не истек ли срок действия учетной записи
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Указывает не заблокирована ли учетная запись.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Указывает не истек ли срок действия учетных данных.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Указывает активна ли учетная запись
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}


