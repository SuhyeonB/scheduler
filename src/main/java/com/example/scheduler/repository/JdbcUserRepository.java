package com.example.scheduler.repository;

import com.example.scheduler.dto.SigninRequestDto;
import com.example.scheduler.dto.UserResponseDto;
import com.example.scheduler.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcUserRepository implements UserRepository{
    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UserResponseDto saveUser(User user) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user").usingGeneratedKeyColumns("user_id");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("email", user.getEmail());
        parameters.put("name", user.getName());
        parameters.put("password", user.getPassword());
        parameters.put("createdAt", Timestamp.valueOf(user.getCreatedAt()));
        parameters.put("updatedAt", Timestamp.valueOf(user.getUpdatedAt()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new UserResponseDto(key.longValue(), user.getName());
    }

    @Override
    public User signin(SigninRequestDto dto) {
        List<User> users = jdbcTemplate.query("select * from user where email = ? and password = ?",
                userRowMapper(), dto.getEmail(), dto.getPassword());
        return users.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));
    }

    @Override
    public int deleteUser(Long user_id) {
        return jdbcTemplate.update("delete from user where user_id = ?", user_id);
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> new User(
                rs.getLong("user_id"),
                rs.getString("email"),
                rs.getString("name"),
                rs.getString("password"),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getTimestamp("updatedAt").toLocalDateTime()
        );
    }
}
