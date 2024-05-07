package com.softgallery.talkativefairytale.dao;

import com.softgallery.talkativefairytale.domain.Character;
import com.softgallery.talkativefairytale.domain.Story;
import com.softgallery.talkativefairytale.domain.User;
import com.softgallery.talkativefairytale.dto.StoryDTO;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class StoryDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Story> storyRowMapper = (resultSet, rowNum) -> {
        Story story = new Story(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("username"),
                resultSet.getString("topic"),
                resultSet.getLong("level"),
                resultSet.getBoolean("isCompleted"),
                resultSet.getString("content"),
                resultSet.getDate("modifiedDate").toLocalDate()
        );

        return story;
    };

    public StoryDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("storytable")
                .usingColumns("title", "username", "topic", "level", "isCompleted", "content", "modifiedDate")
                .usingGeneratedKeyColumns("id");
    }

    public Story findStoriesByNameAndId(String username, Long id) {
        String sql = "SELECT * FROM storytable WHERE username=? AND id=?";
        return jdbcTemplate.queryForObject(sql, Story.class, username, id);
    }

    public Story findStoriesById(Long id) {
        String sql = "SELECT * FROM storytable WHERE id=?";
        return jdbcTemplate.queryForObject(sql, storyRowMapper, id);
    }

    public List<Story> findCompleteStoriesByName(String username) {
        String sql = "SELECT * FROM storytable WHERE username=? AND isCompleted=true";
        return jdbcTemplate.query(sql, storyRowMapper, username);
    }

    public List<Story> findIncompleteStoriesByName(String username) {
        System.out.println(username);
        String sql = "SELECT * FROM storytable WHERE username=? AND isCompleted=false";
        return jdbcTemplate.query(sql, storyRowMapper, username);
    }

    public Long insertNewStory(Story story) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("title", story.getTitle())
                .addValue("username", story.getUsername())
                .addValue("topic", story.getTopic())
                .addValue("level", story.getLevel())
                .addValue("isCompleted", story.getCompleted())
                .addValue("content", story.getContent())
                .addValue("modifiedDate", story.getModifiedDate());

        Number key = this.simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);

        return key.longValue();
    }

    public void updateStoryContent(Long id, String newContent) {
        String sql = "UPDATE storytable SET content=? WHERE id=?";
        jdbcTemplate.update(sql, newContent, id);
    }

    public void deleteStory(Long id) {
        String sql = "DELETE FROM storytable WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
