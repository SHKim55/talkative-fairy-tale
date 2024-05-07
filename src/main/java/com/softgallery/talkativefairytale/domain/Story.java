package com.softgallery.talkativefairytale.domain;

import com.softgallery.talkativefairytale.dto.StoryDTO;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class Story {
    private Long id;
    private String title = "No title";
    private String username;
    private String topic;
    private Long level;
    private Boolean isCompleted;
    private String content;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDate modifiedDate;

    public Story(final Long id, final String title, final String username, final String topic, final Long level, final Boolean isCompleted,
                 final String content, final LocalDate modifiedDate) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.topic = topic;
        this.level = level;
        this.isCompleted = isCompleted;
        this.content = content;
        this.modifiedDate = modifiedDate;
    }

    public Story(final StoryDTO storyDTO) {
        this.title = storyDTO.getTitle();
        this.username = storyDTO.getUsername();
        this.topic = storyDTO.getTopic();
        this.level = storyDTO.getLevel();
        this.isCompleted = storyDTO.getCompleted();
        this.content = storyDTO.getContent();
        this.modifiedDate = storyDTO.getModifiedDate();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() { return title; }

    public String getUsername() {
        return username;
    }

    public String getTopic() {
        return topic;
    }

    public Long getLevel() {
        return level;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(final Boolean completed) {
        isCompleted = completed;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final LocalDate modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
