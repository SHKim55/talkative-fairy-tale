package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.UserDAO;
import com.softgallery.talkativefairytale.domain.User;
import com.softgallery.talkativefairytale.dto.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {
    private final UserDAO userDAO;

    public RegisterService(UserDAO registerDAO) {
        this.userDAO = registerDAO;
    }

    public boolean insertNewUser(UserDTO userDTO) {
        User user = new User(userDTO.getUsername());

        Long id = this.userDAO.insertNewUser(user);
        return true;
    }
}
