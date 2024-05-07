package com.softgallery.talkativefairytale.dto;

import com.softgallery.talkativefairytale.domain.Story;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Repository
public class StoryDTO {
    private Long id;
    private String title = "No Title";
    private String username;
    private String content;
    private String topic;
    private Long level;
    private Boolean isCompleted;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate modifiedDate;

    public StoryDTO() { }

    public StoryDTO(final String title, final String username, final String content, final String topic, final Long level,
                    final Boolean isCompleted, final LocalDate modifiedDate) {
        this.title = title;
        this.username = username;
        this.content = content;
        this.topic = topic;
        this.level = level;
        this.isCompleted = isCompleted;
        this.modifiedDate = modifiedDate;
    }

    public StoryDTO(final Long id, final String title, final String username, final String content, final String topic,
                    final Long level, final Boolean isCompleted, final LocalDate modifiedDate) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.content = content;
        this.topic = topic;
        this.level = level;
        this.isCompleted = isCompleted;
        this.modifiedDate = modifiedDate;
    }

    public StoryDTO(final Story story) {
        this.id = story.getId();
        this.title = story.getTitle();
        this.username = story.getUsername();
        this.content = story.getContent();
        this.topic = story.getTopic();
        this.level = story.getLevel();
        this.isCompleted = story.getCompleted();
        this.modifiedDate = story.getModifiedDate();
    }

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    public String getTopic() {
        return topic;
    }

    public Long getLevel() {
        return level;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }
}
