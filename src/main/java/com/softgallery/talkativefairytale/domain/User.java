package com.softgallery.talkativefairytale.domain;

public class User {
    private final String username;
    private final String password;

    public User(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public User(final Long id, final String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() { return id; }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
