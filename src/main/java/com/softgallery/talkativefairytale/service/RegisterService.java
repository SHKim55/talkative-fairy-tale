package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.UserDAO;
import com.softgallery.talkativefairytale.domain.User;
import com.softgallery.talkativefairytale.dto.UserDTO;
import com.softgallery.talkativefairytale.entity.UserEntity;
import com.softgallery.talkativefairytale.repo.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public boolean insertNewUser(UserDTO userDTO) {
        String username = userDTO.getUsername();
        String password = userDTO.getPassword();

        boolean isExist = userRepository.existsByUsername(username);

        if(isExist) {
            return false;
        }

        UserEntity data = new UserEntity();
        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(data);
        return true;
    }
}
