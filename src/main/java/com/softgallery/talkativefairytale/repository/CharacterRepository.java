package com.softgallery.talkativefairytale.repository;

import com.softgallery.talkativefairytale.entity.CharacterEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {
    @Override
    <S extends CharacterEntity> S save(S entity);

    Optional<CharacterEntity> findByCharacterId(Long characterId);

    Optional<CharacterEntity> findByName(String name);

    List<CharacterEntity> findAllByWhoMade(String whoMade);

    Boolean existsByName(String username);
}
