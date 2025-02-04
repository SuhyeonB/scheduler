package com.example.scheduler.dto;

import com.example.scheduler.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long user_id;
    private String name;

    public UserResponseDto(User user) {
        this.user_id = user.getUser_id();
        this.name = user.getName();
    }
}
