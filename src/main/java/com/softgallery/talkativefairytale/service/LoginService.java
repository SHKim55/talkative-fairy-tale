package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.UserDAO;
import com.softgallery.talkativefairytale.dto.UserDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class LoginService {
    private final UserDAO userDAO;

    public LoginService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void login() {

    }

    public UserDetails loadUserByUserName(final String username) {
        // 회원가입 했다 가정
        UserDTO userDTO = new UserDTO("jh0429");

        if(userDTO.getUsername().equals(username)) {
            // 유저 정보를 만들어서 save
            return User.builder()
                    .username(userDTO.getUsername())
                    .build();
        }
        return null;
    }
}
