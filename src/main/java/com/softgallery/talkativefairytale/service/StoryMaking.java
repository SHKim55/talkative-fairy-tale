package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.CharacterDAO;
import com.softgallery.talkativefairytale.dao.StoryDAO;
import com.softgallery.talkativefairytale.dao.UserDAO;
import com.softgallery.talkativefairytale.domain.Character;
import com.softgallery.talkativefairytale.domain.Story;
import com.softgallery.talkativefairytale.domain.User;
import com.softgallery.talkativefairytale.dto.CharacterDTO;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class StoryMaking {
    private final UserDAO userDAO;
    private final StoryDAO storyDAO;
    private final CharacterDAO characterDAO;

    private User currentUser;
    private Story currentStory;
    private final ArrayList<Character> characters = new ArrayList<>();

    public StoryMaking(final UserDAO userDAO, final StoryDAO storyDAO, final CharacterDAO characterDAO) {
        this.userDAO = userDAO;
        this.storyDAO = storyDAO;
        this.characterDAO = characterDAO;
    }

    private void updateCharacterList(Character character) {
        characters.add(character);
    }

    public CharacterDTO findCharacterById(Long characterId) {
        Character character = characterDAO.findCharacterById(characterId);
        System.out.println("Found character : " + character.getName());
        updateCharacterList(character);
        return new CharacterDTO(character);
    }

    public CharacterDTO findCharacterByName(String characterName) {
        Character character = characterDAO.findCharacterByName(characterName);
        System.out.println("Found character : " + character.getName());
        updateCharacterList(character);
        return new CharacterDTO(character);
    }

    public CharacterDTO insertNewCharacter(CharacterDTO characterDTO) {
        Character character = new Character(
                characterDTO.getName(),
                characterDTO.getGender(),
                characterDTO.getPersonalityGood(),
                characterDTO.getPersonalityBad(),
                characterDTO.getPersonalityNormal(),
                characterDTO.getWhoMade()
        );

        Long id = characterDAO.insertNewCharacter(character);
        return this.findCharacterById(id);
    }
}
