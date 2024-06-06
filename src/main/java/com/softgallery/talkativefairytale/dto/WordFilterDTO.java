package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

@Getter
public class WordFilterDTO {
    boolean flag;
    String reason;

    public WordFilterDTO(boolean flag, String reason) {
        this.flag = flag;
        this.reason = reason;
    }
}
