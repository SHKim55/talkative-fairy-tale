package com.softgallery.talkativefairytale.dto;

public class UserDTO {
    private String username;

    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }
}
