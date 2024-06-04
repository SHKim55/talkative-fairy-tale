package com.softgallery.talkativefairytale.entity;

import com.softgallery.talkativefairytale.service.story.Visibility;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Getter
@Setter
@Table(name="storytable")
public class StoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyId;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String username;

    @Column
    @NotNull
    private String topic;

    @Column
    @NotNull
    private Long level;

    @Column
    private Boolean isCompleted;

    @Column(length = 10000)
    @NotNull
    private String content;

    @Column
    @NotNull
    private LocalDateTime modifiedDate;

    @Column
    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    @NotNull
    private Long likeNum;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    @NotNull
    private Long dislikeNum;
}
