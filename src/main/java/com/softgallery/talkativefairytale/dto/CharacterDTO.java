package com.softgallery.talkativefairytale.dto;

import com.softgallery.talkativefairytale.domain.Character;
import com.softgallery.talkativefairytale.entity.CharacterEntity;

public class CharacterDTO {
    private Long characterId;
    private String name;
    private String gender;
    private String personalityGood;
    private String personalityBad;
    private String personalityNormal;
    private String whoMade;

    public CharacterDTO() { }

    public CharacterDTO(CharacterEntity characterEntity) {
        this.characterId =characterEntity.getCharacterId();
        this.name=characterEntity.getName();
        this.gender=characterEntity.getGender();
        this.personalityGood=characterEntity.getPersonalityGood();
        this.personalityNormal=characterEntity.getPersonalityNormal();
        this.personalityBad=characterEntity.getPersonalityBad();
        this.whoMade=characterEntity.getWhoMade();
    }

    public CharacterDTO(final String name, final String gender, final String personalityGood,
                        final String personalityBad, final String personalityNormal, final String whoMade) {
        this.name = name;
        this.gender = gender;
        this.personalityGood = personalityGood;
        this.personalityBad = personalityBad;
        this.personalityNormal = personalityNormal;
        this.whoMade = whoMade;
    }

    public CharacterDTO(final Long characterId, final String name, final String gender, final String personalityGood,
                        final String personalityBad, final String personalityNormal, final String whoMade) {
        this.characterId = characterId;
        this.name = name;
        this.gender = gender;
        this.personalityGood = personalityGood;
        this.personalityBad = personalityBad;
        this.personalityNormal = personalityNormal;
        this.whoMade = whoMade;
    }

    public CharacterDTO(Character character) {
        this.characterId = character.getId();
        this.name = character.getName();
        this.gender = character.getGender();
        this.personalityGood = character.getPersonalityGood();
        this.personalityBad = character.getPersonalityBad();
        this.personalityNormal = character.getPersonalityNormal();
        this.whoMade = character.getWhoMade();
    }

    public Long getCharacterId() {
        return characterId;
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

    public String getWhoMade() { return whoMade; }
}
