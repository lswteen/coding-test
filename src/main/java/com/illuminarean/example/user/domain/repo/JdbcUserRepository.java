package com.illuminarean.example.user.domain.repo;

import com.illuminarean.example.user.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.illuminarean.example.utils.DateTimeUtils.dateTimeOf;
import static java.util.Optional.ofNullable;

@Repository
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        List<User> results = jdbcTemplate.query(
                "SELECT * FROM `user` WHERE email = ?",
                mapper,
                email
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public Optional<User> findById(long id) {
        List<User> results = jdbcTemplate.query(
                "SELECT * FROM `user` WHERE id = ?",
                mapper,
                id
        );
        return ofNullable(results.isEmpty() ? null : results.get(0));
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE user SET password=?,login_count=?,last_login_date=? WHERE id=?",
                user.getPassword(),
                user.getLoginCount(),
                user.getLastLoginDate(),
                user.getId()
        );
    }

    static RowMapper<User> mapper = (rs, rowNum) ->
            new User.Builder()
                    .id(rs.getLong("id"))
                    .email((rs.getString("email")))
                    .name(rs.getString("name"))
                    .password(rs.getString("password"))
                    .loginCount(rs.getInt("login_count"))
                    .lastLoginDate(dateTimeOf(rs.getTimestamp("last_login_date")))
                    .createDate(dateTimeOf(rs.getTimestamp("create_date")))
                    .build();

}
