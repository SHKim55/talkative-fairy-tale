package com.softgallery.talkativefairytale.service.story;

import com.softgallery.talkativefairytale.auth.JWTUtil;
import com.softgallery.talkativefairytale.data.GPTPromptingInfo;
import com.softgallery.talkativefairytale.dto.*;

import com.softgallery.talkativefairytale.entity.CharacterEntity;
import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.repository.CharacterRepository;
import com.softgallery.talkativefairytale.repository.StoryRepository;
import com.softgallery.talkativefairytale.service.character.CharacterService;
import com.softgallery.talkativefairytale.service.chatGpt.ChatGptService;
import com.softgallery.talkativefairytale.service.chatGpt.Choice;
import com.softgallery.talkativefairytale.service.chatGpt.Message;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class StoryMakingService {
    private final CharacterService characterService;
    private final ChatGptService chatGptService;
    private final StoryRepository storyRepository;
    private final CharacterRepository characterRepository;
    private final GPTPromptingInfo gptPromptingInfo = new GPTPromptingInfo();
    private final JWTUtil jwtUtil;

    private ArrayList<CharacterDTO> characters;

    public StoryMakingService (final CharacterService characterService, final ChatGptService chatGptService,
                               final StoryRepository storyRepository, final CharacterRepository characterRepository,
                               final JWTUtil jwtUtil) {
        this.characterService = characterService;
        this.chatGptService = chatGptService;
        this.storyRepository = storyRepository;
        this.characterRepository = characterRepository;
        this.jwtUtil = jwtUtil;
    }

    private void updateCharacterList(CharacterEntity character) {
        characters.add(new CharacterDTO(character));
    }

    public StoryDTO createStory(String userToken) {
//        characters = new ArrayList<>(List.of(characterService.selectCharacters()));
        String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(userToken));

        // 시스템 프롬프트 적용
        Message message = new Message("system", createGPTQuery(""));
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        // 프롬프트 적용 후 쿼리 날려서 받아오기
        ChatGptResponseDTO responseDTO = chatGptService.askQuestion(new QuestionRequestDTO(messages));
        Choice choice = responseDTO.getChoices().get(0);

        StoryEntity currentStory = new StoryEntity();
        currentStory.setTitle("No Title");
        currentStory.setUsername(username);
        currentStory.setTopic("Default Topic");
        currentStory.setLevel(1L);
        currentStory.setIsCompleted(false);
        currentStory.setContent("<gpt>\n" + choice.getMessage().getContent());
        currentStory.setModifiedDate(LocalDateTime.now());
        currentStory.setLikeNum(0L);
        currentStory.setDislikeNum(0L);
        currentStory.setVisibility(Visibility.PRIVATE);

        StoryEntity savedStory = storyRepository.save(currentStory);

        return new StoryDTO(savedStory);
    }
  
    public StoryDTO addContentToStory(String userToken, Long storyId, Map<String, String> userInput) {
        Optional<StoryEntity> storyEntityOptional = storyRepository.findById(storyId);
        if(storyEntityOptional.isEmpty()) throw new RuntimeException("No such story");

        StoryEntity previousStoryEntity = storyEntityOptional.get();
        StoryDTO previousStoryDTO = new StoryDTO(previousStoryEntity);

        String authorName = jwtUtil.getUsername(JWTUtil.getOnlyToken(userToken));
        if(!authorName.equals(previousStoryDTO.getUsername())) throw new RuntimeException("No Permission to Modify");

        if(previousStoryEntity.getIsCompleted()) throw new RuntimeException("Already completed story");

        // 예외처리 통과 후
        String updatedContent = previousStoryDTO.getContent() + "\n<user>\n" + userInput.get("newStory");

        Message message = new Message("system", createGPTQuery(updatedContent));
        List<Message> messages = new ArrayList<>();
        messages.add(message);

        // 유저가 보낸 내용에 따라 GPT가 응답 생성
        ChatGptResponseDTO responseDTO = chatGptService.askQuestion(new QuestionRequestDTO(messages));
        Choice choice = responseDTO.getChoices().get(0);

        String addedSentence = updatedContent + "\n<gpt>\n" + choice.getMessage().getContent();

        // GPT 응답을 포함하여 DB 저장 및 반환
        previousStoryDTO.setContent(addedSentence);
        previousStoryEntity.setContent(addedSentence);
        storyRepository.save(previousStoryEntity);
  
        return previousStoryDTO;
    }

    public boolean changeStoryStateIncomplete(Long storyId) {
        Optional<StoryEntity> storyEntityOptional = storyRepository.findById(storyId);
        if(storyEntityOptional.isEmpty()) throw new RuntimeException("No such story");

        StoryEntity story = storyEntityOptional.get();
        story.setIsCompleted(false);
        return true;
    }

    public String createGPTQuery(String prevStory) {
        if(prevStory.isEmpty()) {   // 이전 이야기가 없는 경우 (작성 시작 시)
//            CharacterDTO mainCharacter = characters.get(0);
//            CharacterDTO villain = characters.get(1);

            String result = gptPromptingInfo.getInitalizingMessage() + "\n"
                    + gptPromptingInfo.getStyleOptimizingMessage() + "\n\n"
                    + "너는 위 정보를 가지고 이야기의 도입부분을 한국어로 만들어줘야해.\n"
                    ;
//                    + "등장인물의 정보는 아래와 같아.\n\n"
//                    + "<주인공>\n" + mainCharacter.getName() + " : " + mainCharacter.getPersonalityGood() + "\n"
//                    + "<악당>\n" + villain.getName() + " : " + villain.getPersonalityBad() + "\n";

//            for(int i = 2; i < characters.size(); i++) {
//                CharacterDTO character = characters.get(i);
//
//                if(i == 2) {
//                    result += "<조연>\n" + character.getName() + " : " + character.getPersonalityNormal() + "\n";
//                }
//                else {
//                    result += character.getName() + " : " + character.getPersonalityNormal() + "\n";
//                }
//            }

            return result;
        }
        else {  // 이야기에 문장 추가
            String result = gptPromptingInfo.getStoryContinuingMessage(prevStory);
            return result;
        }
    }

    public List<Map<String, String>> findStoryByStoryId(Long storyId) {
        Optional<StoryEntity> storyEntityOptional = storyRepository.findById(storyId);
        if(storyEntityOptional.isEmpty()) throw new RuntimeException("No such story");

        StoryDTO storyDTO = new StoryDTO(storyEntityOptional.get());
        return StoryDTO.parseContents(storyDTO);
    }

//    public StoryDTO findStoryByUsernameAndId(Map<String, String> storyInfo) {
//        Story story = storyDAO.findIncompleteStoriesByName(storyInfo.);
//    }

//    public CharacterDTO findCharacterById(Long characterId) {
////        Character character = characterDAO.findCharacterById(characterId);
////        System.out.println("Found character : " + character.getName());
////        updateCharacterList(character);
////        return new CharacterDTO(character);
//
//        Optional<CharacterEntity> characterOptional = characterRepository.findByCharacterId(characterId);
//        CharacterEntity character = characterOptional.get();
//        System.out.println("Found character : " + character.getName());
//        updateCharacterList(character);
//        return new CharacterDTO(character);
//    }

//    public CharacterDTO findCharacterByName(String characterName) {
////        Character character = characterDAO.findCharacterByName(characterName);
////        System.out.println("Found character : " + character.getName());
////        updateCharacterList(character);
////        return new CharacterDTO(character);
//
//        Optional<CharacterEntity> characterOptional = characterRepository.findByName(characterName);
//        CharacterEntity character = characterOptional.get();
//        System.out.println("Found character : " + character.getName());
//        updateCharacterList(character);
//        return new CharacterDTO(character);
//    }
//
//    public CharacterDTO insertNewCharacter(CharacterDTO characterDTO) {
//        CharacterEntity character = new CharacterEntity();
//        character.setName(characterDTO.getName());
//        character.setGender(characterDTO.getGender());
//        character.setPersonalityGood(characterDTO.getPersonalityGood());
//        character.setPersonalityBad(characterDTO.getPersonalityBad());
//        character.setPersonalityNormal(characterDTO.getPersonalityNormal());
//        character.setWhoMade(character.getWhoMade());
//
//        CharacterEntity savedCharacter = characterRepository.save(character);
//        return new CharacterDTO(savedCharacter);
//    }
}
