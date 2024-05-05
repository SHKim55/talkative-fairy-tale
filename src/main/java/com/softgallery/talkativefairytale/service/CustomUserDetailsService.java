package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dto.CustomUserDetails;
import com.softgallery.talkativefairytale.entity.UserEntity;
import com.softgallery.talkativefairytale.repo.UserRepository;
import org.hibernate.dialect.SybaseASEDialect;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);

        if(userData != null) return new CustomUserDetails(userData);
        return null;
    }
}
