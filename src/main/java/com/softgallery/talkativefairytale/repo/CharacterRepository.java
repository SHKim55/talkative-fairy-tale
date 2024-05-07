package com.softgallery.talkativefairytale.repo;

import com.softgallery.talkativefairytale.entity.CharacterEntity;
import com.softgallery.talkativefairytale.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Integer> {
    Boolean existsByName(String username);
    CharacterEntity findById(Long id);
}
