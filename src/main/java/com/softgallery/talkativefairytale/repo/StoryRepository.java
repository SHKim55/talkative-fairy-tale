package com.softgallery.talkativefairytale.repo;

import com.softgallery.talkativefairytale.entity.StoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
    // Topic에 따른 데이터 조회
    List<StoryEntity> findByTopic(String topic);

    // Topic 및 Type에 따른 정렬된 데이터 조회
    List<StoryEntity> findByTopicOrderByModifiedDateDesc(String topic);
    List<StoryEntity> findByTopicOrderByLikeDesc(String topic);

    // 모든 데이터를 최근 수정일 또는 좋아요 숫자로 정렬하여 조회
    List<StoryEntity> findAllByOrderByModifiedDateDesc();
    List<StoryEntity> findAllByOrderByLikeDesc();

    List<StoryEntity> findAllByTopic();
}