package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.ChatGptResponseDTO;
import com.softgallery.talkativefairytale.dto.QuestionRequestDTO;
import com.softgallery.talkativefairytale.service.ChatGptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-gpt")
public class ChatGptController {

    private final ChatGptService chatGptService;

    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/question")
    public ChatGptResponseDTO sendQuestion(@RequestBody QuestionRequestDTO requestDTO) {
        return chatGptService.askQuestion(requestDTO);
    }
}
