package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StoryNumberDTO {
    private Long storyNum;
    private List<StoryInfoDTO> data;

    public StoryNumberDTO(){}

    public StoryNumberDTO(Long storyNum, List<StoryInfoDTO> data) {
        this.storyNum = storyNum;
        this.data = data;
    }
}
