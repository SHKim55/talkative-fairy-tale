package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class QuestionRequestDTO implements Serializable {
    private String question;

    public QuestionRequestDTO() { }

    public QuestionRequestDTO(final String question) {
        this.question = question;
    }
}
