package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.CharacterDTO;
import com.softgallery.talkativefairytale.service.character.CharacterService;
import com.softgallery.talkativefairytale.service.story.StoryMaking;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/character")
public class CharacterController {
    private final CharacterService characterService;
    private final StoryMaking storyMaking;

    public CharacterController(CharacterService characterService, StoryMaking storyMaking) {
        this.storyMaking = storyMaking;
        this.characterService = characterService;
    }

    @GetMapping("/setCharacterTable")
    public ResponseEntity<String> setCharacterTable() {
        characterService.setCharacterTable();
        return ResponseEntity.ok().body("done");
    }

    @GetMapping("/testSelectCharacters")
    public ResponseEntity<String> testSelecting() {
        CharacterDTO[] selected = characterService.selectCharacters();
        String testOutput="";
        for(int i=0; i<selected.length; i++) {
            CharacterDTO currCharacter = selected[i];
            if(i==0) {
                testOutput+="id: "+currCharacter.getCharacterId()+"/gender: "+currCharacter.getGender()+"/name: "+currCharacter.getName()+"/personality: "+currCharacter.getPersonalityGood()+"\n";
            }
            else if(i==1) {
                testOutput+="id: "+currCharacter.getCharacterId()+"/gender: "+currCharacter.getGender()+"/name: "+currCharacter.getName()+"/personality: "+currCharacter.getPersonalityBad()+"\n";
            }
            else {
                testOutput+="id: "+currCharacter.getCharacterId()+"/gender: "+currCharacter.getGender()+"/name: "+currCharacter.getName()+"/personality: "+currCharacter.getPersonalityNormal()+"\n";
            }
        }
        return ResponseEntity.ok().body(testOutput);
    }

    @PostMapping("/insert")
    public ResponseEntity<CharacterDTO> insertCharacterTest(@RequestBody CharacterDTO characterDTO) {
        CharacterDTO newCharacter = storyMaking.insertNewCharacter(characterDTO);
        return ResponseEntity.created(URI.create("/character/insert/" + newCharacter.getCharacterId())).body(newCharacter);
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<CharacterDTO> readCharacterIdTest(@PathVariable Long id) {
        CharacterDTO foundCharacter = storyMaking.findCharacterById(id);
        return ResponseEntity.ok().body(foundCharacter);
    }
}
