package com.example.scheduler.controller;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.dto.ScheduleUpdateDto;
import com.example.scheduler.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.OK);
    }

    @GetMapping
    public List<ScheduleResponseDto> findAllSchedules(){
        return scheduleService.findAllSchedule();
    }

    @GetMapping("/{schedule_id}")
    public ResponseEntity<ScheduleResponseDto> findById(@PathVariable Long schedule_id) {
        return new ResponseEntity<>(scheduleService.findById(schedule_id), HttpStatus.OK);
    }

    @GetMapping
    public List<ScheduleResponseDto> findByDate(@RequestParam String date){
        return scheduleService.findByDate(date);
    }

    @GetMapping
    public List<ScheduleResponseDto> findByWriter(@RequestParam String writer){
        return scheduleService.findByWriter(writer);
    }

    @PutMapping("/{schedule_id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long schedule_id, @RequestBody ScheduleUpdateDto dto) {
        return new ResponseEntity<>(scheduleService.updateSchedule(schedule_id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{schedule_id}")
    public ResponseEntity<ScheduleResponseDto> deleteSchedule(@PathVariable Long schedule_id, @RequestBody String password) {
        scheduleService.deleteSchedule(schedule_id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
