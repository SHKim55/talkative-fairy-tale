package com.softgallery.talkativefairytale.dao;

import com.softgallery.talkativefairytale.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user")
                .usingColumns("username")
                .usingGeneratedKeyColumns("id");
    }

    public long insertNewUser(User user) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("password", user.getPassword())
                .addValue("username", user.getUsername());
        System.out.println(sqlParameterSource.getValue("password"));
        Number key = this.simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);

        return key.longValue();
    }

}
