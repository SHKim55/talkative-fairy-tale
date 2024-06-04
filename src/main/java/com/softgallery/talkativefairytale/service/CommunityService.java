package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.domain.Story;
import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.dto.StoryNumberDTO;
import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.repo.StoryRepository;
import com.softgallery.talkativefairytale.service.story.Visibility;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommunityService {
    private final StoryRepository storyRepository;

    public CommunityService(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    public StoryNumberDTO getRanker() {
        List<StoryDTO> storyDTOS = getSortedByLike();
        int numberOfItems = Math.min(3, storyDTOS.size());

        List<StoryDTO> rankers = storyDTOS.subList(0, numberOfItems);
        return new StoryNumberDTO((long)numberOfItems, rankers);
    }

    public StoryNumberDTO getBy(String topic, String type) {
        List<StoryDTO> stories;
        if ("all".equalsIgnoreCase(topic)) {
            if ("recent".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findAllByOrderByModifiedDateDesc());
            } else if ("like".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findAllByOrderByLikeDesc());
            } else {
                // 예외 처리 또는 기본 동작을 수행
                throw new RuntimeException("교훈: " + topic + " / 정렬 순서: " + type + "로 찾다가 에러 생김...");
            }
        } else {
            if ("recent".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findByTopicOrderByModifiedDateDesc(topic));
            } else if ("like".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findByTopicOrderByLikeDesc(topic));
            } else {
                // 예외 처리 또는 기본 동작을 수행
                throw new RuntimeException("교훈: " + topic + " / 정렬 순서: " + type + "로 찾다가 에러 생김...");
            }
        }

        return new StoryNumberDTO((long)stories.size(), stories);
    }

    public Set<String> getAllTopics() {
        List<StoryEntity> storyEntities = storyRepository.findAll();
        Set<String> topics = new HashSet<String>();
        for(StoryEntity story:storyEntities) {
            topics.add(story.getTopic());
        }
        return topics;
    }



    private List<StoryDTO> entityListToDto(List<StoryEntity> stories) {
        List<StoryDTO> retStories = new ArrayList<>();
        for(StoryEntity story:stories) {
            retStories.add(entityToDto(story));
        }
        return retStories;
    }

    private StoryDTO entityToDto(StoryEntity story) {
        return new StoryDTO(story.getStoryId(), story.getTitle(), story.getUsername(), story.getContent(),
                story.getTopic(), story.getLevel(), story.getIsCompleted(), story.getModifiedDate(), story.getVisibility(),
                story.getLikeNum(), story.getDislikeNum());
    }

    private List<StoryDTO> getAllPublicStories() {
        List<StoryDTO> retStories = new ArrayList<>();
        List<StoryEntity> stories = storyRepository.findAllByVisibility(Visibility.PUBLIC);

        return entityListToDto(stories);
    }

    private List<StoryDTO> getSortedByLike() {
        List<StoryDTO> storyDTOS = getAllPublicStories();
        Collections.sort(storyDTOS, (a, b) -> b.getLikeNum().compareTo(a.getLikeNum()));
        return storyDTOS;
    }
}
