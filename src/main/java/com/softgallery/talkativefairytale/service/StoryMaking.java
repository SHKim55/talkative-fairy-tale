package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.dao.CharacterDAO;
import com.softgallery.talkativefairytale.dao.StoryDAO;
import com.softgallery.talkativefairytale.dao.UserDAO;
import com.softgallery.talkativefairytale.data.GPTPromptingInfo;
import com.softgallery.talkativefairytale.domain.Character;
import com.softgallery.talkativefairytale.domain.Story;
import com.softgallery.talkativefairytale.domain.User;
import com.softgallery.talkativefairytale.dto.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StoryMaking {
    private final CharacterService characterService;
    private final ChatGptService chatGptService;
    private final UserDAO userDAO;
    private final StoryDAO storyDAO;
    private final CharacterDAO characterDAO;
    private final GPTPromptingInfo gptPromptingInfo = new GPTPromptingInfo();

    private UserDTO currentUser;
    private StoryDTO currentStory;
    private ArrayList<CharacterDTO> characters;

    public StoryMaking(final CharacterService characterService, final ChatGptService chatGptService, final UserDAO userDAO,
                       final StoryDAO storyDAO, final CharacterDAO characterDAO) {
        this.characterService = characterService;
        this.chatGptService = chatGptService;
        this.userDAO = userDAO;
        this.storyDAO = storyDAO;
        this.characterDAO = characterDAO;
    }

    private void updateCharacterList(Character character) {
        characters.add(new CharacterDTO(character));
    }

    public StoryDTO createStory(StoryDTO emptyStoryDTO) {
        characters = new ArrayList<>(List.of(characterService.selectCharacters()));

        String question = createGPTQuery(emptyStoryDTO.getTopic(),
                emptyStoryDTO.getLevel(), "", characters);

        ChatGptResponseDTO responseDTO = chatGptService.askQuestion(new QuestionRequestDTO());
        Choice choice = responseDTO.getChoices().get(0);
        currentStory = new StoryDTO(
                emptyStoryDTO.getTitle(),
                emptyStoryDTO.getUsername(),
                question + "\n" + "<gpt>\n" + choice.getMessage(),
                emptyStoryDTO.getTopic(),
                emptyStoryDTO.getLevel(),
                emptyStoryDTO.getCompleted(),
                emptyStoryDTO.getModifiedDate());

        Long storyId = storyDAO.insertNewStory(new Story(currentStory));

        return currentStory;
    }

//    public StoryDTO addContentToStory(Long idStory, String newContent) {
//        StoryDTO previousStoryDTO = new StoryDTO(storyDAO.findStoriesById(idStory));
//        String updatedContent = previousStoryDTO.getContent() + "<user>\n" + newContent;
//
//        String question = createGPTQuery(previousStoryDTO.getTopic(),
//                previousStoryDTO.getLevel(), updatedContent, characters);
//
//        ChatGptResponseDTO responseDTO = chatGptService.askQuestion(new QuestionRequestDTO(question));
//        Choice choice = responseDTO.getChoices().get(0);
//
//        String addedSentence = updatedContent + "\n<gpt>\n" + choice.getMessage();
//
//        previousStoryDTO.setContent(addedSentence);
//        storyDAO.updateStoryContent(previousStoryDTO.getId(), addedSentence);
//        return previousStoryDTO;
//    }

//    public StoryDTO resumeMakingStory(StoryDTO previousStoryDTO) {
//        characters = new ArrayList<>(List.of(characterService.selectCharacters()));
//
//        currentStory = new StoryDTO(
//                previousStoryDTO.getTitle(),
//                previousStoryDTO.getUsername(),
//                newContent + "\n" + choice.getText(),
//                previousStoryDTO.getTopic(),
//                previousStoryDTO.getLevel(),
//                previousStoryDTO.getCompleted(),
//                previousStoryDTO.getModifiedDate());
//
//        return currentStory;
//    }

    public String createGPTQuery(String topic, Long level, String prevStory, ArrayList<CharacterDTO> characters) {
        if(prevStory.isEmpty()) {   // 이전 이야기가 없는 경우 (작성 시작 시)
            CharacterDTO mainCharacter = characters.get(0);
            CharacterDTO villain = characters.get(1);

            String result = gptPromptingInfo.getInitalizingMessage() + "\n"
                    + gptPromptingInfo.getStyleOptimizingMessage() + "\n\n"
                    + "너는 위 정보를 가지고 이야기의 도입부분을 한국어로 만들어줘야해.\n"
                    + "등장인물의 정보는 아래와 같아.\n\n"
                    + "<주인공>\n" + mainCharacter.getName() + " : " + mainCharacter.getPersonalityGood() + "\n"
                    + "<악당>\n" + villain.getName() + " : " + villain.getPersonalityBad() + "\n";

            for(int i = 2; i < characters.size(); i++) {
                CharacterDTO character = characters.get(i);

                if(i == 2) {
                    result += "<조연>\n" + character.getName() + " : " + character.getPersonalityNormal() + "\n";
                }
                else {
                    result += character.getName() + " : " + character.getPersonalityNormal() + "\n";
                }
            }

            return result;
        }
        else {  // 이야기에 문장 추가
            String result = gptPromptingInfo.getStoryContinuingMessage(topic, level, prevStory);
            return result;
        }
    }

//    public StoryDTO findStoryByUsernameAndId(Map<String, String> storyInfo) {
//        Story story = storyDAO.findIncompleteStoriesByName(storyInfo.);
//    }

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
