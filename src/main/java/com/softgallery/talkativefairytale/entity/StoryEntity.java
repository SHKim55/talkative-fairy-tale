package com.softgallery.talkativefairytale.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="storytable")
public class StoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title = "No Title";

    @Column
    @NotNull
    private String username;

    @Column
    private String topic;

    @Column
    private Long level;

    @Column
    private Boolean isCompleted;

    @Column(length = 10000)
    private String content;

    @Column
    private LocalDate modifiedDate;
}
