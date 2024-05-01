package com.softgallery.talkativefairytale.dao;

import com.softgallery.talkativefairytale.domain.Character;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CharacterDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public CharacterDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    public Character findCharacterById(Long id) {
        String sql = "SELECT * FROM character WHERE id=?";
        return jdbcTemplate.queryForObject(sql, Character.class);
    }

    public Long insertNewCharacter(Character character) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", character.getName())
                .addValue("gender", character.getGender())
                .addValue("personality_good", character.getPersonalityGood())
                .addValue("personality_bad", character.getPersonalityBad())
                .addValue("personality_normal", character.getPersonalityNormal());
        Number key = this.simpleJdbcInsert.executeAndReturnKey(sqlParameterSource);
        return key.longValue();
    }

    public void deleteCharacter(Character character, Long id) {
        String sql = "DELETE FROM character WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
