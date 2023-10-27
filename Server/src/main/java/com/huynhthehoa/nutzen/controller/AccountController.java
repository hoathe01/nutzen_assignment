package com.huynhthehoa.nutzen.controller;

import com.google.gson.Gson;
import com.huynhthehoa.nutzen.payload.BaseResponse;
import com.huynhthehoa.nutzen.payload.request.LoginRequest;
import com.huynhthehoa.nutzen.payload.request.RegisterRequest;
import com.huynhthehoa.nutzen.security.util.JwtHelper;
import com.huynhthehoa.nutzen.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserServiceImp userServiceImp;

    private Gson gson = new Gson();

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
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
        List<?> authoritiy = (List<?>) SecurityContextHolder.getContext().getAuthentication();
        String data = gson.toJson(authoritiy);
        String token = jwtHelper.generateToken(data);
        BaseResponse response = new BaseResponse(200, "", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        boolean isSuccess = userServiceImp.insertUser(registerRequest);
        BaseResponse response = new BaseResponse(200, isSuccess ? "Register success" : "Register faild", isSuccess);
        return ResponseEntity.ok(response);
    }

}
