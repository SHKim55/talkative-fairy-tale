package com.softgallery.talkativefairytale.dto;

public class CharacterDTO {
    private final Long id;
    private final String name;
    private final String gender;
    private final String personalityGood;
    private final String personalityBad;
    private final String personalityNormal;

    public CharacterDTO(final Long id, final String name, final String gender, final String personalityGood,
                     final String personalityBad, final String personalityNormal) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.personalityGood = personalityGood;
        this.personalityBad = personalityBad;
        this.personalityNormal = personalityNormal;
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
