package com.softgallery.talkativefairytale.dto;

import com.softgallery.talkativefairytale.entity.StoryEntity;
import java.time.LocalDateTime;

import com.softgallery.talkativefairytale.service.story.Visibility;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

@Repository
@Getter
public class StoryInfoDTO {
    private Long storyId;
    private String title = "No Title";
    private String username;
    private String topic;
    @DateTimeFormat(pattern = "YYYY-MM-DD")
    private LocalDateTime modifiedDate;
    private Long likeNum;
    private Long dislikeNum;
    public StoryInfoDTO() { }

    public StoryInfoDTO(Long storyId, String title, String username, String topic, LocalDateTime modifiedDate, Long likeNum, Long dislikeNum) {
        this.storyId = storyId;
        this.title = title;
        this.username = username;
        this.topic = topic;
        this.modifiedDate = modifiedDate;
        this.likeNum = likeNum;
        this.dislikeNum = dislikeNum;
    }
}
