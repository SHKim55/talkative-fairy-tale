package com.softgallery.talkativefairytale.controller;

import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.softgallery.talkativefairytale.dto.UserDTO;
import com.softgallery.talkativefairytale.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomepageController {
    private final LoginService loginService;

    public HomepageController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        loginService.login();
        return ResponseEntity.ok().body("hello world");
    }
}
