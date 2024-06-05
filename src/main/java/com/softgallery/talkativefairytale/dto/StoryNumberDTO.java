package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StoryNumberDTO {
    private Long storyNum;
    private List<StoryDTO> data;
    private List<Boolean> evaluated;

    public StoryNumberDTO(){}

    public StoryNumberDTO(Long storyNum, List<StoryDTO> data, List<Boolean> evaluated) {
        this.storyNum = storyNum;
        this.data = data;
    }
}
