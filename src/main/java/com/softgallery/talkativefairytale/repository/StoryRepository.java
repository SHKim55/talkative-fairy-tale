package com.softgallery.talkativefairytale.repository;

import com.softgallery.talkativefairytale.entity.StoryEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<StoryEntity, Long> {
    @Override
    <S extends StoryEntity> S save(S entity);

    @Override
    Optional<StoryEntity> findById(Long aLong);

    Optional<StoryEntity> findByTitle(String Title);

    Optional<StoryEntity> findByUsernameAndStoryId(String username, Long id);

    Optional<StoryEntity> findByUsernameAndIsCompleted(String username, Boolean isCompleted);

    List<StoryEntity> findAllByUsername(String username);


    @Override
    void deleteById(Long aLong);
}
