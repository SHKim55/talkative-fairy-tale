package com.softgallery.talkativefairytale.service.character;

import com.softgallery.talkativefairytale.data.CharacterInfos;
import com.softgallery.talkativefairytale.dto.CharacterDTO;
import com.softgallery.talkativefairytale.entity.CharacterEntity;
import com.softgallery.talkativefairytale.repository.CharacterRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CharacterService {
    private final CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
        setCharacterTable();
    }

    public void setCharacterTable() {
        // male
        for(String[] curr: CharacterInfos.datasM) {
            CharacterEntity characterEntity = new CharacterEntity();
            characterEntity.setName(curr[0]);
            characterEntity.setPersonalityGood((curr[1]));
            characterEntity.setPersonalityNormal(curr[2]);
            characterEntity.setPersonalityBad(curr[3]);
            characterEntity.setGender("0");
            characterRepository.save(characterEntity);
        }

        //female
        for(String[] curr: CharacterInfos.datasF) {
            CharacterEntity characterEntity = new CharacterEntity();
            characterEntity.setName(curr[0]);
            characterEntity.setPersonalityGood((curr[1]));
            characterEntity.setPersonalityNormal(curr[2]);
            characterEntity.setPersonalityBad(curr[3]);
            characterEntity.setGender("1");
            characterRepository.save(characterEntity);
        }
    }

    public CharacterDTO[] selectCharacters() {
        int numOfAssistCharacter=(int)(Math.random()*3)+1;
        int numOfMainCharacter = 1;
        int numOfVillain = 1;

        int numOfTotalCharacter = numOfAssistCharacter + numOfMainCharacter + numOfVillain;

        int numOfMaleCharacter = numOfTotalCharacter%2==0 ? numOfTotalCharacter/2 : numOfTotalCharacter/2+(int)(Math.random()*2);
        int numOfFemaleCharacter = numOfTotalCharacter - numOfMaleCharacter;

        int availCharacterNum = (int)characterRepository.count();

        CharacterDTO[] selectedCharacters = new CharacterDTO[numOfTotalCharacter];

        boolean[] appeared = new boolean[availCharacterNum];
        Arrays.fill(appeared, false);

        while(numOfTotalCharacter>0) {
            int currPick = (int)(Math.random()*availCharacterNum)+1;
            if(appeared[currPick]) continue;
            appeared[currPick] = true;

            Optional<CharacterEntity> currCharacterOptional = characterRepository.findByCharacterId(Long.valueOf(currPick));
            if(currCharacterOptional.isEmpty()) throw new RuntimeException("No such character");

            CharacterDTO currCharacter = new CharacterDTO(currCharacterOptional.get());
            if(currCharacter.getGender().equals("0")) {
                if(numOfMaleCharacter<=0) continue;
                else numOfMaleCharacter--;
            }
            else if(currCharacter.getGender().equals("1")) {
                if(numOfFemaleCharacter<=0) continue;
                else numOfFemaleCharacter--;
            }

            selectedCharacters[--numOfTotalCharacter] = currCharacter;
        }

        return selectedCharacters;
    }
}
