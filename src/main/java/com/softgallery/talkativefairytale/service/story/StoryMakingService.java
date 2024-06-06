package com.softgallery.talkativefairytale.service.story;

import com.softgallery.talkativefairytale.data.GPTPromptingInfo;
import com.softgallery.talkativefairytale.dto.*;

import com.softgallery.talkativefairytale.entity.CharacterEntity;
import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.repository.CharacterRepository;
import com.softgallery.talkativefairytale.repository.StoryRepository;
import com.softgallery.talkativefairytale.service.character.CharacterService;
import com.softgallery.talkativefairytale.service.chatGpt.ChatGptService;
import com.softgallery.talkativefairytale.service.chatGpt.Choice;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StoryMakingService {
    private final CharacterService characterService;
    private final ChatGptService chatGptService;
    private final StoryRepository storyRepository;
    private final CharacterRepository characterRepository;
    private final GPTPromptingInfo gptPromptingInfo = new GPTPromptingInfo();

    private StoryEntity currentStory;
    private ArrayList<CharacterDTO> characters;

    public StoryMakingService (final CharacterService characterService, final ChatGptService chatGptService,
                       final StoryRepository storyRepository, final CharacterRepository characterRepository) {
        this.characterService = characterService;
        this.chatGptService = chatGptService;
        this.storyRepository = storyRepository;
        this.characterRepository = characterRepository;
    }

    private void updateCharacterList(CharacterEntity character) {
        characters.add(new CharacterDTO(character));
    }

    public StoryDTO createStory(StoryDTO emptyStoryDTO) {
        characters = new ArrayList<>(List.of(characterService.selectCharacters()));

        String question = createGPTQuery(emptyStoryDTO.getTopic(),
                emptyStoryDTO.getLevel(), "", characters);

        ChatGptResponseDTO responseDTO = chatGptService.askQuestion(new QuestionRequestDTO());
        Choice choice = responseDTO.getChoices().get(0);

        currentStory = new StoryEntity();
        currentStory.setTitle(emptyStoryDTO.getTitle());
        currentStory.setUsername(emptyStoryDTO.getUsername());
        currentStory.setTopic(emptyStoryDTO.getTopic());
        currentStory.setLevel(currentStory.getLevel());
        currentStory.setIsCompleted(emptyStoryDTO.getCompleted());
        currentStory.setContent(question + "\n" + "<gpt>\n" + choice.getMessage());
        currentStory.setModifiedDate(emptyStoryDTO.getModifiedDate());

//        currentStory = new StoryEntity(
//                emptyStoryDTO.getTitle(),
//                emptyStoryDTO.getUsername(),
//                emptyStoryDTO.getTopic(),
//                emptyStoryDTO.getLevel(),
//                emptyStoryDTO.getCompleted(),
//                question + "\n" + "<gpt>\n" + choice.getMessage(),
//                emptyStoryDTO.getModifiedDate());

        StoryEntity savedStory = storyRepository.save(currentStory);

        return new StoryDTO(savedStory);
    }

    public StoryDTO addContentToStory(Long idStory, String newContent) {
//        Optional<StoryEntity> storyEntityOptional = storyRepository.findById(idStory);
//        if(storyEntityOptional.isEmpty()) throw new RuntimeException("No such story");
//
//        StoryEntity previousStoryEntity = storyEntityOptional.get();
//        StoryDTO previousStoryDTO = new StoryDTO(previousStoryEntity);
//
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
////        previousStoryDTO.setContent(addedSentence);
////        storyDAO.updateStoryContent(previousStoryDTO.getId(), addedSentence);
//
//        previousStoryEntity.setContent(addedSentence);
//        storyRepository.save(previousStoryEntity);

        return null;
    }

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
//        Character character = characterDAO.findCharacterById(characterId);
//        System.out.println("Found character : " + character.getName());
//        updateCharacterList(character);
//        return new CharacterDTO(character);

        Optional<CharacterEntity> characterOptional = characterRepository.findByCharacterId(characterId);
        CharacterEntity character = characterOptional.get();
        System.out.println("Found character : " + character.getName());
        updateCharacterList(character);
        return new CharacterDTO(character);
    }

    public CharacterDTO findCharacterByName(String characterName) {
//        Character character = characterDAO.findCharacterByName(characterName);
//        System.out.println("Found character : " + character.getName());
//        updateCharacterList(character);
//        return new CharacterDTO(character);

        Optional<CharacterEntity> characterOptional = characterRepository.findByName(characterName);
        CharacterEntity character = characterOptional.get();
        System.out.println("Found character : " + character.getName());
        updateCharacterList(character);
        return new CharacterDTO(character);
    }

    public CharacterDTO insertNewCharacter(CharacterDTO characterDTO) {
        CharacterEntity character = new CharacterEntity();
        character.setName(characterDTO.getName());
        character.setGender(characterDTO.getGender());
        character.setPersonalityGood(characterDTO.getPersonalityGood());
        character.setPersonalityBad(characterDTO.getPersonalityBad());
        character.setPersonalityNormal(characterDTO.getPersonalityNormal());
        character.setWhoMade(character.getWhoMade());

        CharacterEntity savedCharacter = characterRepository.save(character);
        return new CharacterDTO(savedCharacter);
    }
}
