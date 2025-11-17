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

@Data
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private UUID id;
    private String username;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

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
@Override
// проверяет не истек ли срок аккаунта
public boolean isAccountNonExpired(){
    return true;
    }
    @Override
    // проверяет не заблокирован ли аккаунт
    public boolean isAccountNonLocked(){
        return true;
    }
    @Override
    // срок действия учетных данных не истек
    public boolean isCredentialsNonExpired(){
        return true;
    }
    @Override
    // проверяет включен ли аккаунт
    public boolean isEnabled() {
        return true;
    }
}


