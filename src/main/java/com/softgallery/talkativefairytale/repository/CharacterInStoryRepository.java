package com.softgallery.talkativefairytale.repository;

import com.softgallery.talkativefairytale.entity.CharacterInStoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterInStoryRepository extends JpaRepository<CharacterInStoryEntity, Long> {


}
