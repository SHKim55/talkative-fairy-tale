package com.softgallery.talkativefairytale.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="`character`")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String characterName;

    // 0: male 1:female
    @Column
    private int gender;

    @Column
    private String personality_good;

    @Column String personality_bad;

    @Column String personality_normal;
}
