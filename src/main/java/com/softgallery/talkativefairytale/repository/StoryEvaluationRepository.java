package com.softgallery.talkativefairytale.repository;

import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.entity.StoryEvaluationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryEvaluationRepository extends JpaRepository<StoryEvaluationEntity, Long> {
    boolean existsByUsernameAndStoryId(String username, Long storyId);
}
