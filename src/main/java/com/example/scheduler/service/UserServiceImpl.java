package com.example.scheduler.service;

import com.example.scheduler.dto.SigninRequestDto;
import com.example.scheduler.dto.SignupRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.entity.User;
import com.example.scheduler.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto saveUser(SignupRequestDto dto) {
        User user = new User(dto.getEmail(), dto.getName(), dto.getPassword());

        return userRepository.saveUser(user);
    }

    @Override
    public UserResponseDto signin(SigninRequestDto dto) {
        User user = userRepository.signin(dto); // orElseThrow
        return new UserResponseDto(user);
    }

    @Override
    public void deleteUser(Long user_id) {
        int deleteRow = userRepository.deleteUser(user_id);

        if (deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "DOES NOT EXIST");
        }
    }
}
