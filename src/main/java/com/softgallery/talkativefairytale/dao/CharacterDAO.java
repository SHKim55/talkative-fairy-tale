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
                resultSet.getString("personality_good"),
                resultSet.getString("personality_bad"),
                resultSet.getString("personality_normal")
        );

        return character;
    };

    public CharacterDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("`character`")
                .usingColumns("name", "gender", "personality_good", "personality_bad", "personality_normal")
                .usingGeneratedKeyColumns("id_character");
    }

    public Character findCharacterById(Long id) {
        String sql = "SELECT * FROM `character` WHERE id=?";
        return jdbcTemplate.queryForObject(sql, characterRowMapper, id);
    }

    public Character findCharacterByName(String name) {
        String sql = "SELECT * FROM `character` WHERE name=?";
        return jdbcTemplate.queryForObject(sql, Character.class, name);
    }

    public Long insertNewCharacter(Character character) {
        System.out.println(character.getPersonalityGood());

        SqlParameterSource sqlParameterSource = new MapSqlParameterSource()
                .addValue("name", character.getName())
                .addValue("gender", character.getGender())
                .addValue("personality_good", character.getPersonalityGood())
                .addValue("personality_bad", character.getPersonalityBad())
                .addValue("personality_normal", character.getPersonalityNormal());
        Number key = this.simpleJdbcInsert.executeAndReturnKey(sqlParameterSource).longValue();
        return key.longValue();
    }

    public void deleteCharacter(Character character, Long id) {
        String sql = "DELETE FROM `character` WHERE id=?";
        jdbcTemplate.update(sql, id);
    }
}
