package com.huynhthehoa.nutzen.service.imp;

import com.huynhthehoa.nutzen.entity.UserEntity;
import com.huynhthehoa.nutzen.payload.request.RegisterRequest;
import com.huynhthehoa.nutzen.payload.request.UpdateRequest;
import com.huynhthehoa.nutzen.payload.response.UserResponse;

import java.util.List;

public interface UserServiceImp {
    List<UserResponse> getListUsers();
    boolean insertUser(RegisterRequest registerRequest);
    boolean updateUser(UpdateRequest updateRequest);
}
