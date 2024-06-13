package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.auth.JWTUtil;
import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.dto.StoryNumberDTO;
import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.entity.StoryEvaluationEntity;
import com.softgallery.talkativefairytale.repository.StoryEvaluationRepository;
import com.softgallery.talkativefairytale.repository.StoryRepository;
import com.softgallery.talkativefairytale.service.story.Visibility;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommunityService {
    private final StoryRepository storyRepository;
    private final StoryEvaluationRepository storyEvaluationRepository;

    private final JWTUtil jwtUtil;

    public CommunityService(StoryRepository storyRepository, StoryEvaluationRepository storyEvaluationRepository, JWTUtil jwtUtil) {
        this.storyRepository = storyRepository;
        this.storyEvaluationRepository = storyEvaluationRepository;
        this.jwtUtil = jwtUtil;
    }

    public StoryNumberDTO getRanker(String token) {
        String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(token));
        List<StoryDTO> storyDTOS = getSortedByLike();
        int numberOfItems = Math.min(3, storyDTOS.size());

        List<StoryDTO> rankers = storyDTOS.subList(0, numberOfItems);
        List<Boolean> evaluated = this.getEvaluated(rankers, username);

        return new StoryNumberDTO((long)numberOfItems, rankers, evaluated);
    }

    public StoryNumberDTO getBy(String topic, String type, String token) {
        String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(token));
        List<StoryDTO> stories;
        if ("all".equalsIgnoreCase(topic)) {
            if ("recent".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findAllByVisibilityAndIsCompletedTrueOrderByModifiedDate(Visibility.PUBLIC));
            } else if ("like".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findAllByVisibilityAndIsCompletedTrueOrderByLikeNum(Visibility.PUBLIC));
            } else {
                // 예외 처리 또는 기본 동작을 수행
                throw new RuntimeException("교훈: " + topic + " / 정렬 순서: " + type + "로 찾다가 에러 생김...");
            }
        } else {
            if ("recent".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findAllByTopicAndVisibilityAndIsCompletedTrueOrderByLikeNum(topic, Visibility.PUBLIC));
            } else if ("like".equalsIgnoreCase(type)) {
                stories = entityListToDto(storyRepository.findAllByTopicAndVisibilityAndIsCompletedTrueOrderByLikeNum(topic, Visibility.PUBLIC));
            } else {
                // 예외 처리 또는 기본 동작을 수행
                throw new RuntimeException("교훈: " + topic + " / 정렬 순서: " + type + "로 찾다가 에러 생김...");
            }
        }

        List<Boolean> evaluated = this.getEvaluated(stories, username);

        return new StoryNumberDTO((long)stories.size(), stories, evaluated);
    }

    public Set<String> getAllTopics() {
        List<StoryEntity> storyEntities = storyRepository.findAllByVisibilityAndIsCompletedTrue(Visibility.PUBLIC);
        Set<String> topics = new HashSet<String>();
        for(StoryEntity story:storyEntities) {
            topics.add(story.getTopic());
        }
        return topics;
    }

    public StoryDTO addEvaluation(Long likeOrUnlike, Long storyId, String token) {
        String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(token));
        Optional<StoryEntity> story = storyRepository.findById(storyId);

        if(story.isPresent()) {
            if(storyEvaluationRepository.existsByUsernameAndStoryId(username, storyId)) {
                throw new RuntimeException("이미 평가한 이야기 입니다");
            }
            else {
                StoryEntity ret = story.get();
                if(likeOrUnlike>0) {
                    ret.setLikeNum(ret.getLikeNum()+1);
                    storyRepository.save(ret);
                }
                else {
                    ret.setDislikeNum(ret.getDislikeNum()+1);
                    storyRepository.save(ret);
                }

                storyEvaluationRepository.save(new StoryEvaluationEntity(username, storyId));
                return entityToDto(ret);
            }
        }
        else {
            throw new RuntimeException("아이디가 " + storyId + "인 동화가 존재하지 않습니다");
        }
    }

    private List<StoryDTO> getAllPublicStories() {
        List<StoryDTO> retStories = new ArrayList<>();
        List<StoryEntity> stories = storyRepository.findAllByVisibilityAndIsCompletedTrue(Visibility.PUBLIC);

        return entityListToDto(stories);
    }

    private List<StoryDTO> getSortedByLike() {
        List<StoryDTO> storyDTOS = getAllPublicStories();
        Collections.sort(storyDTOS, (a, b) -> b.getLikeNum().compareTo(a.getLikeNum()));
        return storyDTOS;
    }

    private List<Boolean> getEvaluated(List<StoryDTO> storyDTOS, String username) {
        List<Boolean> evaluated = new ArrayList<Boolean>();

        for(StoryDTO storyDTO:storyDTOS) {
            if(storyDTO.getUsername().equals(username)) {
                evaluated.add(true);
                continue;
            }
            evaluated.add(false);
        }
        return evaluated;
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
}
