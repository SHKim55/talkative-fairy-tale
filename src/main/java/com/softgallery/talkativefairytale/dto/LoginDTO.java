package com.softgallery.talkativefairytale.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginDTO {
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
