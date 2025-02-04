package com.example.scheduler.service;

import com.example.scheduler.dto.SigninRequestDto;
import com.example.scheduler.dto.SignupRequestDto;
import com.example.scheduler.dto.UserResponseDto;

public interface UserService {
    UserResponseDto saveUser(SignupRequestDto dto);
    UserResponseDto signin(SigninRequestDto dto);
    void deleteUser(Long user_id);
}
