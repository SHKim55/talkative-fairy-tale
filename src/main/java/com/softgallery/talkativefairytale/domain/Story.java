package com.softgallery.talkativefairytale.domain;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class Story {
    private final Long id;
    private final String username;
    private final String topic;
    private final Long level;
    private Boolean isCompleted;
    private String content;
    @DateTimeFormat(pattern = "YYYY-MM-DD;HH:MM:SS")
    private LocalDateTime modifiedDate;

    public Story(final Long id, final String username, final String topic, final Long level, final Boolean isCompleted,
                 final String content, final LocalDateTime modifiedDate) {
        this.id = id;
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

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(final LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
