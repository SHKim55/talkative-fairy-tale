package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.ChatGptResponseDTO;
import com.softgallery.talkativefairytale.dto.QuestionRequestDTO;
import com.softgallery.talkativefairytale.service.chatGpt.ChatGptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    @Value("${chatgpt.api-key}")
    private String gptToken;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponseDTO sendQuestion(@RequestBody QuestionRequestDTO requestDTO) {
        return chatGptService.askQuestion(requestDTO);
    }

//    @GetMapping("/token")
//    public ResponseEntity<String> getToken() {
//        return ResponseEntity.ok().body(this.gptToken);
//    }
}
