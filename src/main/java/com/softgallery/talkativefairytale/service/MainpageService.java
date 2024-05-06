package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.StoryDAO;
import com.softgallery.talkativefairytale.dao.UserDAO;
import com.softgallery.talkativefairytale.domain.Story;
import com.softgallery.talkativefairytale.dto.StoryDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MainpageService {
    private final UserDAO userDAO;
    private final StoryDAO storyDAO;

    public MainpageService(UserDAO userDAO, StoryDAO storyDAO) {
        this.userDAO = userDAO;
        this.storyDAO = storyDAO;
    }

    public ArrayList<StoryDTO> findIncompleteStoriesMadeByUserName(String username) {
        ArrayList<StoryDTO> storyDTOs = new ArrayList<>();
        List<Story> stories = storyDAO.findIncompleteStoriesByName(username);

        for(Story story : stories) {
            storyDTOs.add(new StoryDTO(story));
        }
        return storyDTOs;
    }

    public ArrayList<StoryDTO> findCompleteStoriesMadeByUserName(String username) {
        ArrayList<StoryDTO> storyDTOs = new ArrayList<>();
        List<Story> stories = storyDAO.findCompleteStoriesByName(username);

        for(Story story : stories) {
            storyDTOs.add(new StoryDTO(story));
        }
        return storyDTOs;
    }
}
