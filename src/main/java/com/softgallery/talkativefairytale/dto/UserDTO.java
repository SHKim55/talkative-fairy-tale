package com.softgallery.talkativefairytale.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private String username;
    private String password;

    public UserDTO() {};

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
