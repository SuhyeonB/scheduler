package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long schedule_id;
    private String todo;
    // how to define a 'many-to-one' relationship using JDBC????
    private Long user_id;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
