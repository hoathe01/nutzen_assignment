package com.huynhthehoa.nutzen.service;

import com.huynhthehoa.nutzen.entity.RoleEntity;
import com.huynhthehoa.nutzen.entity.UserEntity;
import com.huynhthehoa.nutzen.payload.request.RegisterRequest;
import com.huynhthehoa.nutzen.payload.response.RoleResponse;
import com.huynhthehoa.nutzen.payload.response.UserResponse;
import com.huynhthehoa.nutzen.repository.UserRepository;
import com.huynhthehoa.nutzen.service.imp.UserServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class UserService implements UserServiceImp {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserResponse> getListUsers() {
        try {
            List<UserEntity> listEntity = userRepository.findAll();
            return listEntity.stream().map(
                    userEntity -> UserResponse
                            .builder()
                            .email(userEntity.getEmail())
                            .username(userEntity.getUsername())
                            .role(new RoleResponse(userEntity.getRole().getName()))
                            .build()
            ).toList();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public boolean insertUser(RegisterRequest registerRequest) {

        try {
            UserEntity userEntity = UserEntity.builder()
                    .username(registerRequest.getUsername())
                    .email(registerRequest.getEmail())
                    .password(passwordEncoder.encode(registerRequest.getPassword()))
                    .role(RoleEntity.builder()
                            .id(registerRequest.getRole())
                            .build())
                    .build();
            userRepository.save(userEntity);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return false;
        }
    }

}
