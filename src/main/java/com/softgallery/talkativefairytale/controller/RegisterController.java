package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.UserDTO;
import com.softgallery.talkativefairytale.service.RegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register/user")
    public String registerNewUser(@RequestBody UserDTO userDTO) {
        registerService.insertNewUser(userDTO);
        return "redirect:/";
    }
}
