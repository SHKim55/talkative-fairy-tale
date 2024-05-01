package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final UserDAO userDAO;

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void login() {

    }
}
