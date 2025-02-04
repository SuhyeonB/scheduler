package com.example.scheduler.controller;

import com.example.scheduler.dto.SigninRequestDto;
import com.example.scheduler.dto.SignupRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody SignupRequestDto dto) {
        return new ResponseEntity<>(userService.saveUser(dto), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponseDto> signin(@RequestBody SigninRequestDto dto) {
        return new ResponseEntity<>(userService.signin(dto), HttpStatus.OK);
    }

//    @PutMapping("/{user_id}")
//    public ResponseEntity<> updateUser(@PathVariable Long user_id, @RequestBody String name, String password) {
//        return new ResponseEntity<>(userService.updateUser(user_id, name, password), HttpStatus.OK);
//    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> deleteUser(@PathVariable Long user_id) {
        userService.deleteUser(user_id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
