package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.StoryDAO;
import com.softgallery.talkativefairytale.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service
public class MainpageService {
    private final UserDAO userDAO;
    private final StoryDAO storyDAO;

    public MainpageService(UserDAO userDAO, StoryDAO storyDAO) {
        this.userDAO = userDAO;
        this.storyDAO = storyDAO;
    }
}
