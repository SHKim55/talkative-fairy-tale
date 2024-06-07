package com.softgallery.talkativefairytale.repository;

import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.service.story.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
    @Override
    <S extends StoryEntity> S save(S entity);

    @Override
    Optional<StoryEntity> findById(Long aLong);

    // Topic에 따른 데이터 조회
    List<StoryEntity> findByTopic(String topic);

    // Topic 및 Type에 따른 정렬된 데이터 조회
    List<StoryEntity> findAllByTopicAndVisibilityAndIsCompletedTrueOrderByModifiedDateDesc(String topic, Visibility visibility);
    List<StoryEntity> findAllByTopicAndVisibilityAndIsCompletedTrueOrderByLikeNumDesc(String topic, Visibility visibility);


    // 모든 데이터를 최근 수정일 또는 좋아요 숫자로 정렬하여 조회
    List<StoryEntity> findAllByVisibilityAndIsCompletedTrueOrderByModifiedDateDesc(Visibility visibility);
    List<StoryEntity> findAllByVisibilityAndIsCompletedTrueOrderByLikeNumDesc(Visibility visibility);

    List<StoryEntity> findAllByVisibilityAndIsCompletedTrue(Visibility visibility);
    List<StoryEntity> findAllByTopic(String topic);

    List<StoryEntity> findAllByUsernameAndIsCompletedOrderByModifiedDateDesc(String username, Boolean isCompleted);


    @Override
    void deleteById(Long aLong);
}