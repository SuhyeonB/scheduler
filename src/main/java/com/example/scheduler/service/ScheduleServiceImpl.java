package com.example.scheduler.service;

import com.example.scheduler.dto.ScheduleRequestDto;
import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.dto.ScheduleUpdateDto;
import com.example.scheduler.entity.Schedule;
import com.example.scheduler.entity.User;
import com.example.scheduler.repository.JdbcUserRepository;
import com.example.scheduler.repository.ScheduleRepository;
import com.example.scheduler.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final JdbcUserRepository jdbcUserRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, JdbcUserRepository jdbcUserRepository) {
        this.scheduleRepository = scheduleRepository;
        this.jdbcUserRepository = jdbcUserRepository;
        this.userRepository = jdbcUserRepository;
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
        if (dto.getTodo() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todo is empty");
        }

        // 해당 유저가 존재하는지 먼저 확인
        User user = userRepository.findById(dto.getUser_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        // 비밀번호 검증
        if (!user.getPassword().equals(dto.getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }

        // 해당 유저의 일정인지 확인
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        if (!schedule.getUser().getUser_id().equals(dto.getUser_id())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You do not have permission to update this schedule");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, dto.getTodo());

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist");
        }
        Schedule updated = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(updated);
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
