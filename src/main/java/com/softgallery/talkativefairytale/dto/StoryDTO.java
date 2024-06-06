package com.softgallery.talkativefairytale.dto;

import com.softgallery.talkativefairytale.entity.StoryEntity;
import java.time.LocalDateTime;

import com.softgallery.talkativefairytale.service.story.Visibility;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class StoryDTO {
    private Long storyId;
    private String title = "No Title";
    private String username;
    private String content;
    private String topic;
    private Long level;
    private Boolean isCompleted;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDateTime modifiedDate;
    private Visibility visibility;
    private Long likeNum;
    private Long dislikeNum;

    public StoryDTO() { }

    public StoryDTO(final String title, final String username, final String content, final String topic, final Long level,
                    final Boolean isCompleted, final LocalDateTime modifiedDate) {
        this.title = title;
        this.username = username;
        this.content = content;
        this.topic = topic;
        this.level = level;
        this.isCompleted = isCompleted;
        this.modifiedDate = modifiedDate;
    }

    public StoryDTO(final Long id, final String title, final String username, final String content, final String topic,
                    final Long level, final Boolean isCompleted, final LocalDateTime modifiedDate, final Visibility visibility,
                    final Long likeNum, final Long dislikeNum) {
        this.storyId = id;
        this.title = title;
        this.username = username;
        this.content = content;
        this.topic = topic;
        this.level = level;
        this.isCompleted = isCompleted;
        this.modifiedDate = modifiedDate;
        this.visibility = visibility;
        this.likeNum = likeNum;
        this.dislikeNum = dislikeNum;
    }

    public StoryDTO(final StoryEntity storyEntity) {
        this.storyId = storyEntity.getStoryId();
        this.title = storyEntity.getTitle();
        this.username = storyEntity.getUsername();
        this.content = storyEntity.getContent();
        this.topic = storyEntity.getTopic();
        this.level = storyEntity.getLevel();
        this.isCompleted = storyEntity.getIsCompleted();
        this.modifiedDate = storyEntity.getModifiedDate();
        this.visibility = storyEntity.getVisibility();
        this.likeNum = storyEntity.getLikeNum();
        this.dislikeNum = storyEntity.getDislikeNum();
    }

    public void setContent(String content) { this.content = content; }
}
