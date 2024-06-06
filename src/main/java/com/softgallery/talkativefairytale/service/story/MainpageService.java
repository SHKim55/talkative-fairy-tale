package com.softgallery.talkativefairytale.service.story;

import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.repository.StoryRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MainpageService {
    private final StoryRepository storyRepository;

    public MainpageService(final StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public ArrayList<StoryDTO> findIncompleteStoriesMadeByUserName(String username) {
        ArrayList<StoryDTO> storyDTOs = new ArrayList<>();
        List<StoryEntity> stories = storyRepository.findAllByUsernameAndIsCompleted(username, false);

        for(StoryEntity storyEntity : stories) {
            storyDTOs.add(new StoryDTO(storyEntity));
        }
        return storyDTOs;
    }

    public ArrayList<StoryDTO> findCompleteStoriesMadeByUserName(String username) {
        ArrayList<StoryDTO> storyDTOs = new ArrayList<>();
        List<StoryEntity> stories = storyRepository.findAllByUsernameAndIsCompleted(username, true);

        for(StoryEntity storyEntity : stories) {
            storyDTOs.add(new StoryDTO(storyEntity));
        }
        return storyDTOs;
    }
}