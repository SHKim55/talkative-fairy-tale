package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class QuestionRequestDto implements Serializable {
    private List<Message> messages;

}
