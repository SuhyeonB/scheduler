package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);
    Schedule findByIdOrElseThrow(Long id);
    List<ScheduleResponseDto> findAllSchedules();
    List<ScheduleResponseDto> findByDate(String date);
    List<ScheduleResponseDto> findByWriter(String writer);
    int updateSchedule(Long id, String todo);
    int deleteSchedule(Long id);
}
