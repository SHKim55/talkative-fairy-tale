package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class StoryMakingPageService {
    private UserDAO userDAO;

    public StoryMakingPageService(final UserDAO userDAO) {
        this.userDAO = userDAO;
    }
}
