package com.example.scheduler.dto;

import com.example.scheduler.entity.Schedule;
import com.example.scheduler.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
    private Long schedule_id;
    private String todo;
    private String user_name;
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule){
        this.schedule_id = schedule.getSchedule_id();
        this.todo = schedule.getTodo();
        //this.writer;
        this.user_name = schedule.getUser().getName();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
