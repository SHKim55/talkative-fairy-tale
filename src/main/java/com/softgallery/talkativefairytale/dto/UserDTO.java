package com.softgallery.talkativefairytale.dto;

import com.softgallery.talkativefairytale.domain.User;

public class UserDTO {
    private Long id;
    private String username;

    public UserDTO() { }

    public UserDTO(final String username) {
        this.username = username;
    }

    public UserDTO(final Long id, final String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO(final User user) {
        this.id = user.getId();
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
}
