package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.CharacterDTO;
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
        return ResponseEntity.ok().body("done");
    }

    @GetMapping("/testSelectCharacters")
    public ResponseEntity<String> testSelecting() {
        CharacterDTO[] selected = characterService.selectCharacters();
        String testOutput="";
        for(int i=0; i<selected.length; i++) {
            CharacterDTO currCharacter = selected[i];
            if(i==0) {
                testOutput+="id: "+currCharacter.getId()+"/gender: "+currCharacter.getGender()+"/name: "+currCharacter.getName()+"/personality: "+currCharacter.getPersonalityGood()+"\n";
            }
            else if(i==1) {
                testOutput+="id: "+currCharacter.getId()+"/gender: "+currCharacter.getGender()+"/name: "+currCharacter.getName()+"/personality: "+currCharacter.getPersonalityBad()+"\n";
            }
            else {
                testOutput+="id: "+currCharacter.getId()+"/gender: "+currCharacter.getGender()+"/name: "+currCharacter.getName()+"/personality: "+currCharacter.getPersonalityNormal()+"\n";
            }
        }
        return ResponseEntity.ok().body(testOutput);
    }
}
