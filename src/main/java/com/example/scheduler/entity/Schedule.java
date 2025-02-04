package com.example.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long schedule_id;
    private String todo;
    private User user;
    private String writer;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String todo, User user) {
        this.todo = todo;
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // DB 조회용 생성자
    public Schedule(Long schedule_id, String todo, Long user_id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.schedule_id = schedule_id;
        this.todo = todo;
        this.user = new User(user_id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
