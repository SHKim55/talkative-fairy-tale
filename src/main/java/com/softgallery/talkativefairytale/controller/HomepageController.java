package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class HomepageController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("auth OK");
    }

    @PostMapping("/test")
    public ResponseEntity<String> testPost(UserDTO userDTO) {
        return ResponseEntity.ok().body("hello world");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        System.out.println("here");
        return ResponseEntity.ok().body("hello world");
    }
}
