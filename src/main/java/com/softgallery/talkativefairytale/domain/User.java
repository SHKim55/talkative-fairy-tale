package com.softgallery.talkativefairytale.domain;

public class User {
    private Long id;
    private String username;

    public User(final String username) {
        this.username = username;
    }

    public User(final Long id, final String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() {
        return username;
    }
}
