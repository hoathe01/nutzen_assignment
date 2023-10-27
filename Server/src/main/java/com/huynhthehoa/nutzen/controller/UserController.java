package com.huynhthehoa.nutzen.controller;

import com.huynhthehoa.nutzen.payload.BaseResponse;
import com.huynhthehoa.nutzen.repository.UserRepository;
import com.huynhthehoa.nutzen.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserServiceImp userService;
    @GetMapping
    public ResponseEntity<?> getListUser() {
        List<?> UsersResponse = userService.getListUsers();
        BaseResponse response = new BaseResponse(200, "List User", UsersResponse);
        return ResponseEntity.ok(response);
    }
}
