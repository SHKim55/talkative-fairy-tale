package com.softgallery.talkativefairytale.service.story;

import com.softgallery.talkativefairytale.auth.JWTUtil;
import com.softgallery.talkativefairytale.dto.StoryInfoDTO;
import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.repository.StoryRepository;
import java.util.List;

import com.softgallery.talkativefairytale.service.community.CommunityService;
import org.springframework.stereotype.Service;

@Service
public class MainpageService {
    private final StoryRepository storyRepository;
    private final JWTUtil jwtUtil;

    public MainpageService(final StoryRepository storyRepository, JWTUtil jwtUtil) {
        this.storyRepository = storyRepository;
        this.jwtUtil = jwtUtil;
    }

    public List<StoryInfoDTO> findIncompleteStoriesMadeByUserName(String userToken) {
        String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(userToken));
        List<StoryEntity> stories = storyRepository.findAllByUsernameAndIsCompletedOrderByModifiedDateDesc(username, false);

        return CommunityService.entityListToInfo(stories);
    }

    public List<StoryInfoDTO> findCompleteStoriesMadeByUserName(String userToken) {
        String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(userToken));
        List<StoryEntity> stories = storyRepository.findAllByUsernameAndIsCompletedOrderByModifiedDateDesc( username, true);

        return CommunityService.entityListToInfo(stories);
    }
}