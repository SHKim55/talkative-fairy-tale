package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.CharacterDTO;
import com.softgallery.talkativefairytale.service.MainpageService;
import com.softgallery.talkativefairytale.service.StoryMaking;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MainpageController {
    private final MainpageService mainpageService;
    private final StoryMaking storyMaking;

    public MainpageController(MainpageService mainpageService, StoryMaking storyMaking) {
        this.mainpageService = mainpageService;
        this.storyMaking = storyMaking;
    }

    @PostMapping("/character/insert")
    public ResponseEntity<CharacterDTO> insertCharacterTest(@RequestBody CharacterDTO characterDTO) {
        CharacterDTO newCharacter = storyMaking.insertNewCharacter(characterDTO);
        return ResponseEntity.created(URI.create("/character/insert/" + newCharacter.getId())).body(newCharacter);
    }

    @GetMapping("/character/find/id/{id}")
    public ResponseEntity<CharacterDTO> readCharacterIdTest(@PathVariable Long id) {
        CharacterDTO foundCharacter = storyMaking.findCharacterById(id);
        return ResponseEntity.ok().body(foundCharacter);
    }
}
