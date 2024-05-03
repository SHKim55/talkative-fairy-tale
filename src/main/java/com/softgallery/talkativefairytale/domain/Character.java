package com.softgallery.talkativefairytale.domain;

public class Character {
    private Long id;
    private String name;
    private String gender;
    private String personalityGood;
    private String personalityBad;
    private String personalityNormal;

    public Character(final Long id, final String name, final String gender, final String personalityGood,
                     final String personalityBad, final String personalityNormal) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.personalityGood = personalityGood;
        this.personalityBad = personalityBad;
        this.personalityNormal = personalityNormal;
    }

    // DB 등록 전 임시 객체 생성자
    public Character(final String name, final String gender, final String personalityGood,
                     final String personalityBad, final String personalityNormal) {
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
