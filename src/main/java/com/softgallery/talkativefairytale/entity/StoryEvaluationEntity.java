package com.softgallery.talkativefairytale.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "story_evaluation")
public class StoryEvaluationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluationId;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private Long storyId;

    public StoryEvaluationEntity(String username, Long storyId) {
        this.username = username;
        this.storyId = storyId;
    }
}
