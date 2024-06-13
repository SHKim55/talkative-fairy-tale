package com.softgallery.talkativefairytale.entity;

import jakarta.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="`character`")
public class CharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long characterId;

    @Column
    @NotNull
    private String name;

    // 0: male 1: female
    @Column
    @NotNull
    private String gender;

    @Column
    private String personalityGood;

    @Column
    private String personalityBad;

    @Column
    private String personalityNormal;

    @Column
    @NotNull
    private String whoMade;
}
