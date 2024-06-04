package com.softgallery.talkativefairytale.service.story;

import com.softgallery.talkativefairytale.dao.StoryDAO;
import com.softgallery.talkativefairytale.dao.UserDAO;
import com.softgallery.talkativefairytale.domain.Story;
import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.repository.StoryRepository;
import com.softgallery.talkativefairytale.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MainpageService {
    private final UserDAO userDAO;
    private final StoryDAO storyDAO;

    private final UserRepository userRepository;

    private final StoryRepository storyRepository;

    public MainpageService(UserDAO userDAO, StoryDAO storyDAO, final UserRepository userRepository, final StoryRepository storyRepository) {
        this.userDAO = userDAO;
        this.storyDAO = storyDAO;

        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }

//    public ArrayList<StoryDTO> findIncompleteStoriesMadeByUserName(String username) {
//        ArrayList<StoryDTO> storyDTOs = new ArrayList<>();
//        List<Story> stories = storyDAO.findIncompleteStoriesByName(username);
//
//        for(Story story : stories) {
//            storyDTOs.add(new StoryDTO(story));
//        }
//        return storyDTOs;
//    }

//    public ArrayList<StoryDTO> findCompleteStoriesMadeByUserName(String username) {
//        ArrayList<StoryDTO> storyDTOs = new ArrayList<>();
//        List<Story> stories = storyDAO.findCompleteStoriesByName(username);
//
//        for(Story story : stories) {
//            storyDTOs.add(new StoryDTO(story));
//        }
//        return storyDTOs;
//    }
}
