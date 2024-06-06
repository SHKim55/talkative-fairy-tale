package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

@Getter
public class WordFilterDTO {
    boolean bad;
    String reason;

    public WordFilterDTO(boolean flag, String reason) {
        this.bad = flag;
        this.reason = reason;
    }
}
