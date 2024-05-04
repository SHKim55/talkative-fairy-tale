package com.softgallery.talkativefairytale.dto;

import com.softgallery.talkativefairytale.domain.User;

public class UserDTO {
    private Long id;
    private String username;

    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(final Long id, final String username) {
        this.id = id;
        this.username = username;
    }

//    public UserDTO(final User user) {
//        this.id = user.getId();
//        this.username = username;
//    }

    public Long getId() { return id; }
    public String getUsername() { return username; }

    public String getPassword() {
        return password;
    }
}
