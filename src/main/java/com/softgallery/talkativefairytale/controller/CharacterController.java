package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.service.CharacterService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/character")
public class CharacterController {
    private CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("hello");
    }

    @GetMapping("/setCharacterTable")
    public ResponseEntity<String> setCharacterTable() {
        characterService.setCharacterTable();
        return ResponseEntity.ok().body("get");
    }


}
