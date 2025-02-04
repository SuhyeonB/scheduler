package com.example.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
    private String todo;
    // String writer -> user_id
    private Long user_id;
}
