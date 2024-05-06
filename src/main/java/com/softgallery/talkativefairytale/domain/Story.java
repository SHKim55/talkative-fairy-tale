package com.softgallery.talkativefairytale.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class Story {
    private final Long id;
    private String title = "No title";
    private final String username;
    private final String topic;
    private final Long level;
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
