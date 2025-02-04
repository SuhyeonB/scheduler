package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.dto.ScheduleUpdateDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
    ScheduleResponseDto findById(Long id);
    List<ScheduleResponseDto> findAllSchedule();
    List<ScheduleResponseDto> findByDate(String date);
    List<ScheduleResponseDto> findByWriter(String writer);
    ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateDto dto);
    ScheduleResponseDto deleteSchedule(Long id, String password);
}
