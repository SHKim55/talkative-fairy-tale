package com.softgallery.talkativefairytale.service.story;

import com.softgallery.talkativefairytale.repository.StoryRepository;
import com.softgallery.talkativefairytale.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MainpageService {
    private final UserRepository userRepository;

    private final StoryRepository storyRepository;

    public MainpageService(final UserRepository userRepository, final StoryRepository storyRepository) {
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
