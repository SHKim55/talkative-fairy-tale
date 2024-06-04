package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StoryNumberDTO {
    private Long storyNum;
    private List<StoryDTO> data;

    public StoryNumberDTO(){}

    public StoryNumberDTO(Long storyNum, List<StoryDTO> data) {
        this.storyNum = storyNum;
        this.data = data;
    }
}
