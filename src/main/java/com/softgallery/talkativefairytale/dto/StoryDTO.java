package com.softgallery.talkativefairytale.dto;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Repository
public class StoryDTO {
    private Long id;
    private String username;
    private String content;
    private String topic;
    private Long level;
    private Boolean isCompleted;
    @DateTimeFormat(pattern = "YYYY-MM-DD;HH:MM:SS")
    private LocalDateTime modifiedDate;

    public StoryDTO() { }

    public StoryDTO(final String username, final String content, final String topic, final Long level,
                    final Boolean isCompleted, final LocalDateTime modifiedDate) {
        this.username = username;
        this.content = content;
        this.topic = topic;
        this.level = level;
        this.isCompleted = isCompleted;
        this.modifiedDate = modifiedDate;
    }

    public StoryDTO(final Long id, final String username, final String content, final String topic,
                    final Long level, final Boolean isCompleted, final LocalDateTime modifiedDate) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.topic = topic;
        this.level = level;
        this.isCompleted = isCompleted;
        this.modifiedDate = modifiedDate;
    }

    public Long getId() { return id; }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
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

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }
}
