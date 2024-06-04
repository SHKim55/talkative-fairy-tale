package com.softgallery.talkativefairytale.repository;

import com.softgallery.talkativefairytale.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Override
    <S extends UserEntity> S save(S entity);

    @Override
    boolean existsById(Long aLong);

    @Override
    Optional<UserEntity> findById(Long aLong);

    Boolean existsByUsername(String username);

    UserEntity findByUsername(String username);

    @Override
    void deleteById(Long aLong);
}
