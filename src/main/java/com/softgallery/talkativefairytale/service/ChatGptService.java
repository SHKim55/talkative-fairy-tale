package com.softgallery.talkativefairytale.service;

import com.softgallery.talkativefairytale.ChatGptConfig;
import com.softgallery.talkativefairytale.dto.ChatGptRequestDTO;
import com.softgallery.talkativefairytale.dto.ChatGptResponseDTO;
import com.softgallery.talkativefairytale.dto.QuestionRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ChatGptService {

    private static RestTemplate restTemplate = new RestTemplate();

    public HttpEntity<ChatGptRequestDTO> buildHttpEntity(ChatGptRequestDTO requestDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfig.MEDIA_TYPE));
        headers.add(ChatGptConfig.AUTHORIZATION, ChatGptConfig.BEARER + ChatGptConfig.API_KEY);
        return new HttpEntity<>(requestDto, headers);
    }

    public ChatGptResponseDTO getResponse(HttpEntity<ChatGptRequestDTO> chatGptRequestDtoHttpEntity) {
        ResponseEntity<ChatGptResponseDTO> responseEntity = restTemplate.postForEntity(
                ChatGptConfig.URL,
                chatGptRequestDtoHttpEntity,
                ChatGptResponseDTO.class);

        return responseEntity.getBody();
    }

    public ChatGptResponseDTO askQuestion(QuestionRequestDTO requestDto) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequestDTO(
                                ChatGptConfig.MODEL,
                                requestDto.getMessages(),
                                ChatGptConfig.MAX_TOKEN,
                                ChatGptConfig.TEMPERATURE,
                                ChatGptConfig.TOP_P
                        )
                )
        );
    }
}
