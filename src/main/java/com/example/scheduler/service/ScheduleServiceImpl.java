package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.dto.ScheduleUpdateDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.entity.User;
import com.example.scheduler.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
        User user = new User(dto.getUser_id());
        Schedule schedule = new Schedule(dto.getTodo(), user);

        return scheduleRepository.saveSchedule(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule() {
        return scheduleRepository.findAllSchedules();
    }

    @Override
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);

        return new ScheduleResponseDto(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findByDate(String date) {
        return scheduleRepository.findByDate(date);
    }

    @Override
    public List<ScheduleResponseDto> findByWriter(String writer) {
        return scheduleRepository.findByWriter(writer);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateDto dto) {
        if (todo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todo does not exist");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, todo);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist");
        }
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public ScheduleResponseDto deleteSchedule(Long id) {
        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist");
        }
        return null;
    }
}
