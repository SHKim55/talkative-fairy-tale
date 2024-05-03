package com.softgallery.talkativefairytale.repo;

import com.softgallery.talkativefairytale.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

}
