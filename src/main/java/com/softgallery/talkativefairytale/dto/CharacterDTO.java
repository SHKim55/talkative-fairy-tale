package com.softgallery.talkativefairytale.dto;

import com.softgallery.talkativefairytale.domain.Character;
import com.softgallery.talkativefairytale.entity.CharacterEntity;

public class CharacterDTO {
    private Long id;
    private String name;
    private String gender;
    private String personalityGood;
    private String personalityBad;
    private String personalityNormal;

    public CharacterDTO() { }

    public CharacterDTO(CharacterEntity characterEntity) {
        this.id=characterEntity.getId();
        this.name=characterEntity.getCharacterName();
        this.gender=characterEntity.getGender();
        this.personalityGood=characterEntity.getPersonality_good();
        this.personalityNormal=characterEntity.getPersonality_normal();
        this.personalityBad=characterEntity.getPersonality_bad();
    }

    public CharacterDTO(final String name, final String gender, final String personalityGood,
                        final String personalityBad, final String personalityNormal) {
        this.name = name;
        this.gender = gender;
        this.personalityGood = personalityGood;
        this.personalityBad = personalityBad;
        this.personalityNormal = personalityNormal;
    }

    public CharacterDTO(final Long id, final String name, final String gender, final String personalityGood,
                     final String personalityBad, final String personalityNormal) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.personalityGood = personalityGood;
        this.personalityBad = personalityBad;
        this.personalityNormal = personalityNormal;
    }

    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.name = character.getName();
        this.gender = character.getGender();
        this.personalityGood = character.getPersonalityGood();
        this.personalityBad = character.getPersonalityBad();
        this.personalityNormal = character.getPersonalityNormal();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPersonalityGood() {
        return personalityGood;
    }

    public String getPersonalityBad() {
        return personalityBad;
    }

    public String getPersonalityNormal() {
        return personalityNormal;
    }
}
