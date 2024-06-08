package com.softgallery.talkativefairytale.service.story;

import com.softgallery.talkativefairytale.auth.JWTUtil;
import com.softgallery.talkativefairytale.data.GPTPromptingInfo;
import com.softgallery.talkativefairytale.dto.*;

import com.softgallery.talkativefairytale.entity.CharacterEntity;
import com.softgallery.talkativefairytale.entity.StoryEntity;
import com.softgallery.talkativefairytale.repository.CharacterRepository;
import com.softgallery.talkativefairytale.repository.StoryEvaluationRepository;
import com.softgallery.talkativefairytale.repository.StoryRepository;
import com.softgallery.talkativefairytale.service.CommunityService;
import com.softgallery.talkativefairytale.service.character.CharacterService;
import com.softgallery.talkativefairytale.service.chatGpt.ChatGptService;
import com.softgallery.talkativefairytale.service.chatGpt.Choice;
import com.softgallery.talkativefairytale.service.chatGpt.Message;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class StoryMakingService {
    private final CharacterService characterService;
    private final ChatGptService chatGptService;
    private final StoryRepository storyRepository;
    private final CharacterRepository characterRepository;

    private final StoryEvaluationRepository storyEvaluationRepository;
    private final GPTPromptingInfo gptPromptingInfo = new GPTPromptingInfo();
    private final JWTUtil jwtUtil;

    private ArrayList<CharacterDTO> characters;

    public StoryMakingService (final CharacterService characterService, final ChatGptService chatGptService,
                               final StoryRepository storyRepository, final CharacterRepository characterRepository, StoryEvaluationRepository storyEvaluationRepository,
                               final JWTUtil jwtUtil) {
        this.characterService = characterService;
        this.chatGptService = chatGptService;
        this.storyRepository = storyRepository;
        this.characterRepository = characterRepository;
        this.storyEvaluationRepository = storyEvaluationRepository;
        this.jwtUtil = jwtUtil;
    }

    private void updateCharacterList(CharacterEntity character) {
        characters.add(new CharacterDTO(character));
    }

//    public StoryDTO createStory(String userToken) {
////        characters = new ArrayList<>(List.of(characterService.selectCharacters()));
//        String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(userToken));
//        Message message = new Message("system", createGPTQuery(""));
//        List<Message> messages = new ArrayList<>();
//        messages.add(message);
//
//        // 프롬프트 적용 후 쿼리 날려서 받아오기
//        ChatGptResponseDTO responseDTO = chatGptService.askQuestion(new QuestionRequestDTO(messages));
//        Choice choice = responseDTO.getChoices().get(0);
//
//        StoryEntity currentStory = new StoryEntity();
//        currentStory.setTitle("No Title");
//        currentStory.setUsername(username);
//        currentStory.setTopic("Default Topic");
//        currentStory.setLevel(1L);
//        currentStory.setIsCompleted(false);
//        currentStory.setContent("<gpt>\n" + choice.getMessage().getContent());
//        currentStory.setModifiedDate(LocalDateTime.now());
//        currentStory.setLikeNum(0L);
//        currentStory.setDislikeNum(0L);
//        currentStory.setVisibility(Visibility.PRIVATE);
//
//        StoryEntity savedStory = storyRepository.save(currentStory);
//
//        return new StoryDTO(savedStory);
//    }
    private List<Map<String, String>> parseContents(StoryDTO storyDTO) {
        List<Map<String, String>> parsedContents = new ArrayList<>();

        // 정규 표현식을 사용하여 <태그>를 기준으로 문자열을 분리
        Pattern pattern = Pattern.compile("(<[^>]+>)(.*?)(?=<[^>]+>|$)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(storyDTO.getContent());

        while (matcher.find()) {
            String tag = matcher.group(1);
            String content = matcher.group(2).trim();

            // 태그와 내용을 출력
            System.out.println("Tag: " + tag);
            System.out.println("Content: " + content);

            HashMap<String, String> paragraph = new HashMap<>();
            paragraph.put(tag, content);
            parsedContents.add(paragraph);
        }

        return parsedContents;
    }

    private String createGPTQuery(String prevStory) {
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

    private List<String> getRecommendedTitleAndTopic(String content) {
//        String queryStatement = gptPromptingInfo.getTitleAndTopicRecommendationMessage()
//                + "\n" + content;
//
//        Message message = new Message("system", queryStatement);
//        List<Message> messages = new ArrayList<>();
//        messages.add(message);
//
//        ChatGptResponseDTO responseDTO = chatGptService.askQuestion(new QuestionRequestDTO(messages));
//        Choice choice = responseDTO.getChoices().get(0);
//        System.out.println(choice.getMessage().getContent());
//
//        List<String> values = new ArrayList<>();
//        values.add(choice.getMessage().getContent());

        return null;
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
        StoryDTO savedStoryDTO = new StoryDTO(savedStory);
        savedStoryDTO.setContent(choice.getMessage().getContent());

        return savedStoryDTO;
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

        // 수정시간 최신화 후 GPT 응답을 포함하여 DB 저장 준비
        previousStoryEntity.setModifiedDate(LocalDateTime.now());
        previousStoryEntity.setContent(addedSentence);

        // 이야기 종료
        if(choice.getMessage().getContent().contains(gptPromptingInfo.getClosingMessage())) {
            previousStoryEntity.setIsCompleted(true);

            List<String> values = getRecommendedTitleAndTopic(addedSentence);
            previousStoryEntity.setTitle(values.get(0));   // Title
            previousStoryEntity.setTopic(values.get(1));   // Topic
        }

        StoryEntity updatedStoryEntity = storyRepository.save(previousStoryEntity);
        StoryDTO updatedStoryDTO = new StoryDTO(updatedStoryEntity);
        updatedStoryDTO.setContent(choice.getMessage().getContent());   // 가장 마지막에 만든 문장만 반환하도록 처리
  
        return updatedStoryDTO;
    }

    public boolean changeStoryStateIncomplete(Long storyId) {
        Optional<StoryEntity> storyEntityOptional = storyRepository.findById(storyId);
        if(storyEntityOptional.isEmpty()) throw new RuntimeException("No such story");

        StoryEntity story = storyEntityOptional.get();
        story.setIsCompleted(false);
        return true;
    }

    public List<String> findStoryByStoryId(Long storyId) {
        Optional<StoryEntity> storyEntityOptional = storyRepository.findById(storyId);
        if(storyEntityOptional.isEmpty()) throw new RuntimeException("No such story");

        StoryDTO storyDTO = new StoryDTO(storyEntityOptional.get());
        return StoryDTO.parseContents(storyDTO);
    }

    public boolean changeStoryVisibility(Long storyId, String visibility) {
        Optional<StoryEntity> storyEntityOptional = storyRepository.findById(storyId);
        if(storyEntityOptional.isEmpty()) throw new RuntimeException("No such story");

        StoryEntity storyEntity = storyEntityOptional.get();
        if(visibility.equals("PRIVATE"))
            storyEntity.setVisibility(Visibility.PRIVATE);
        else if(visibility.equals("PUBLIC"))
            storyEntity.setVisibility(Visibility.PUBLIC);
        else
            throw new RuntimeException("Invalid Visibility Value");

        storyRepository.save(storyEntity);
        return true;
    }

    public StoryReadingDTO getStory(Long storyId, String token) {
        Optional<StoryEntity> story = storyRepository.findById(storyId);
        if(!story.isPresent()) throw new RuntimeException("story entity 없음");
        else {
            String username = jwtUtil.getUsername(JWTUtil.getOnlyToken(token));
            StoryEntity currStory = story.get();

            Boolean isEvaluated = storyEvaluationRepository.existsByUsernameAndStoryId(username, currStory.getStoryId());

            StoryReadingDTO storyReadingDTO = new StoryReadingDTO(
                    currStory.getStoryId(), currStory.getTitle(), currStory.getUsername(),
                    currStory.getTopic(), currStory.getIsCompleted(), currStory.getModifiedDate(), currStory.getVisibility(),
                    currStory.getLikeNum(), currStory.getDislikeNum(), username.equals(currStory.getUsername()), isEvaluated
                    );
            return storyReadingDTO;
        }
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
