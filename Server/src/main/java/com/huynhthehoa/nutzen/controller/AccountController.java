package com.huynhthehoa.nutzen.controller;

import com.google.gson.Gson;
import com.huynhthehoa.nutzen.payload.BaseResponse;
import com.huynhthehoa.nutzen.payload.request.LoginRequest;
import com.huynhthehoa.nutzen.payload.request.RegisterRequest;
import com.huynhthehoa.nutzen.payload.request.UpdateRequest;
import com.huynhthehoa.nutzen.security.util.JwtHelper;
import com.huynhthehoa.nutzen.service.imp.UserServiceImp;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("account")
@Slf4j
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserServiceImp userServiceImp;

    private Gson gson = new Gson();

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword());
        authenticationManager.authenticate(authentication);
        List<?> authoritiy = (List<?>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String data = gson.toJson(authoritiy);
        String token = jwtHelper.generateToken(data);
        BaseResponse response = new BaseResponse(200, "", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("logout")
    public ResponseEntity<?> logout() {

        SecurityContextHolder.clearContext();
        List<GrantedAuthority> authoritiy =  new ArrayList<>();
        authoritiy.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
        log.info(authoritiy.toString());
        String data = gson.toJson(authoritiy);
        String token = jwtHelper.generateToken(data);
        BaseResponse response = new BaseResponse(200, "", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        boolean isSuccess = userServiceImp.insertUser(registerRequest);
        BaseResponse response = new BaseResponse(200, isSuccess ? "Register success" : "Register failed", isSuccess);
        return ResponseEntity.ok(response);
    }

    @PutMapping("update")
    public ResponseEntity<?> updateInfo(@Valid @RequestBody UpdateRequest updateRequest){
        boolean isSuccess = userServiceImp.updateUser(updateRequest);
        BaseResponse response = new BaseResponse(200, isSuccess ? "Update success" : "Update failed", isSuccess);
        return ResponseEntity.ok(response);
    }

}
