package com.huynhthehoa.nutzen.security;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huynhthehoa.nutzen.security.util.JwtHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtHelper jwtHelper;
    private Gson gson = new Gson();
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            String data = jwtHelper.validToken(token);
            if (data != null && !data.isEmpty()) {
                Type jsonAuth = new TypeToken<ArrayList<SimpleGrantedAuthority>>() {}.getType();
                List<GrantedAuthority> auth = gson.fromJson(data,jsonAuth);
                UsernamePasswordAuthenticationToken user =
                        new UsernamePasswordAuthenticationToken("Protected","Protected",auth);
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }
        filterChain.doFilter(request, response);
    }
}
