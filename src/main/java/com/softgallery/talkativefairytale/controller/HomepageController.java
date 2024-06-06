package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.UserDTO;
import com.softgallery.talkativefairytale.dto.WordFilterDTO;
import com.softgallery.talkativefairytale.service.moderation.WordFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
public class HomepageController {
    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("auth OK");
    }

    @PostMapping("/test/moderation")
    public WordFilterDTO testPost(@RequestBody UserDTO userDTO) {
        return WordFilter.doFilterWithGptModeration(userDTO.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDTO userDTO) {
        System.out.println("here");
        return ResponseEntity.ok().body("hello world");
    }
}
