package com.softgallery.talkativefairytale.dao;

import com.softgallery.talkativefairytale.domain.Character;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class CharacterDAO {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Character> characterRowMapper = (resultSet, rowNum) -> {
        Character character = new Character(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("gender"),
                resultSet.getString("personalityGood"),
                resultSet.getString("personalityBad"),
                resultSet.getString("personalityNormal"),
                resultSet.getString("whoMade")
        );

        return character;
    };

    public CharacterDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("charactertable")
                .usingColumns("name", "gender", "personalityGood", "personalityBad", "personalityNormal", "whoMade")
                .usingGeneratedKeyColumns("id");
    }

    public Character findCharacterById(Long id) {
        String sql = "SELECT * FROM charactertable WHERE id=?";
        return jdbcTemplate.queryForObject(sql, characterRowMapper, id);
    }

    public Character findCharacterByName(String name) {
        String sql = "SELECT * FROM charactertable WHERE name=?";
        return jdbcTemplate.queryForObject(sql, Character.class, name);
    }

    public Long insertNewCharacter(Character character) {
        System.out.println(character.getPersonalityGood());

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", character.getName())
                .addValue("gender", character.getGender())
                .addValue("personalityGood", character.getPersonalityGood())
                .addValue("personalityBad", character.getPersonalityBad())
                .addValue("personalityNormal", character.getPersonalityNormal())
                .addValue("whoMade", character.getWhoMade());
        Number key = this.simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return key.longValue();
    }

    public void deleteCharacter(Long id) {
        String sql = "DELETE FROM charactertable WHERE characterId=?";
        jdbcTemplate.update(sql, id);
    }
}
