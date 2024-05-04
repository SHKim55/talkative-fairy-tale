package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.data.CharacterInfos;
import com.softgallery.talkativefairytale.entity.CharacterEntity;
import com.softgallery.talkativefairytale.repo.CharacterRepository;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public void setCharacterTable() {
        // male
        for(String[] curr: CharacterInfos.datasM) {
            CharacterEntity characterEntity = new CharacterEntity();
            characterEntity.setCharacterName(curr[0]);
            characterEntity.setPersonality_good((curr[1]));
            characterEntity.setPersonality_normal(curr[2]);
            characterEntity.setPersonality_bad(curr[3]);
            characterEntity.setGender(0);
            characterRepository.save(characterEntity);
        }

        //female
        for(String[] curr: CharacterInfos.datasF) {
            CharacterEntity characterEntity = new CharacterEntity();
            characterEntity.setCharacterName(curr[0]);
            characterEntity.setPersonality_good((curr[1]));
            characterEntity.setPersonality_normal(curr[2]);
            characterEntity.setPersonality_bad(curr[3]);
            characterEntity.setGender(1);
            characterRepository.save(characterEntity);
        }
    }
}
