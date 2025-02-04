package com.example.scheduler.repository;

import com.example.scheduler.dto.SigninRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.entity.User;

public interface UserRepository {
    UserResponseDto saveUser(User user);
    User signin(SigninRequestDto dto);
    int deleteUser(Long user_id);
}
