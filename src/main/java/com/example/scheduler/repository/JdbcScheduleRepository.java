package com.example.scheduler.repository;

import com.example.scheduler.dto.ScheduleResponseDto;
import com.example.scheduler.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcScheduleRepository implements ScheduleRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcScheduleRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("todo", schedule.getTodo());
        parameters.put("user_user_id", schedule.getUser().getUser_id());
        parameters.put("createdAt", Timestamp.valueOf(schedule.getCreatedAt()));
        parameters.put("updatedAt", Timestamp.valueOf(schedule.getUpdatedAt()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        // user_name 가져오기
        String username = jdbcTemplate.queryForObject("SELECT u.name FROM schedule s JOIN user u ON s.user_user_id = u.user_id WHERE s.schedule_id = ?", String.class, key.longValue());

        return new ScheduleResponseDto(key.longValue(), schedule.getTodo(), schedule.getUser().getName(), schedule.getUpdatedAt());
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedules() {
        return jdbcTemplate
                .query("""
            SELECT s.schedule_id, s.todo, s.updatedAt, 
                   u.user_id, u.email, u.name, u.password, u.createdAt, u.updatedAt
            FROM schedule s
            JOIN user u ON s.user_user_id = u.user_id
            """, scheduleRowMapper());
    }

    @Override
    public Schedule findByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate
                .query("""
            SELECT s.schedule_id, s.todo, s.createdAt, s.updatedAt, u.user_id, u.name
            FROM schedule s
            JOIN user u ON s.user_user_id = u.user_id
            WHERE s.schedule_id = ?
        """, scheduleRowMapper2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public List<ScheduleResponseDto> findByDate(String date) {
        List<ScheduleResponseDto> result = jdbcTemplate
                .query("""
            SELECT s.schedule_id, s.todo, s.updatedAt, u.name
            FROM schedule s
            JOIN user u ON s.user_user_id = u.user_id
            WHERE DATE(s.updatedAt) = ?
        """, scheduleRowMapper(), Date.valueOf(date));
        return result;
    }

    @Override
    public List<ScheduleResponseDto> findByWriter(String writer) {
        List<ScheduleResponseDto> result = jdbcTemplate
                .query("""
            SELECT s.schedule_id, s.todo, s.updatedAt, u.name
            FROM schedule s
            JOIN user u ON s.user_user_id = u.user_id
            WHERE u.name = ?
        """, scheduleRowMapper(), writer);
        return result;
    }

    @Override
    public int updateSchedule(Long id, String todo) {
        LocalDateTime now = LocalDateTime.now();
        return jdbcTemplate.update("update schedule set todo = ?, updatedAt = ? where schedule_id = ?", todo, Timestamp.valueOf(now), id);
    }

    @Override
    public int deleteSchedule(Long id) {
        return jdbcTemplate.update("delete from schedule where schedule_id = ?", id);
    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("schedule_id"),
                        rs.getString("todo"),
                        rs.getString("name"),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }
    private RowMapper<Schedule> scheduleRowMapper2(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("schedule_id"),
                        rs.getString("todo"),
                        rs.getLong("user_user_id"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("updatedAt").toLocalDateTime()
                );
            }
        };
    }
}
