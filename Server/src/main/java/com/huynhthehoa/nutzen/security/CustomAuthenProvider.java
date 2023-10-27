package com.huynhthehoa.nutzen.security;

import com.huynhthehoa.nutzen.entity.UserEntity;
import com.huynhthehoa.nutzen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomAuthenProvider implements AuthenticationProvider {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Get Username and password from user request
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = userRepository.findByEmail(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                List<GrantedAuthority> auth = new ArrayList<>();
                auth.add(new SimpleGrantedAuthority(user.getRole().getName()));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword(), auth);
                SecurityContextHolder.getContext().setAuthentication(token);
                return token;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
