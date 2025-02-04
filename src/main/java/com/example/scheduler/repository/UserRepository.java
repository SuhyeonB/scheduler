package com.example.scheduler.repository;

import com.example.scheduler.dto.SigninRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.entity.User;

import java.util.Optional;

public interface UserRepository {
    UserResponseDto saveUser(User user);
    User signin(SigninRequestDto dto);
    int deleteUser(Long user_id);

    Optional<User> findById(Long user_id);
}
