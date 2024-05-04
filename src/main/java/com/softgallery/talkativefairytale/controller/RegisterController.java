package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.UserDTO;
import com.softgallery.talkativefairytale.service.RegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseEntity<Boolean> registerNewUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok().body(registerService.insertNewUser(userDTO));
    }
}
