package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.dto.UserDTO;
import com.softgallery.talkativefairytale.service.MainpageService;
import com.softgallery.talkativefairytale.service.StoryMaking;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainpageController {
    private final MainpageService mainpageService;
    private final StoryMaking storyMaking;

    private ArrayList<StoryDTO> completeStories = new ArrayList<>();
    private ArrayList<StoryDTO> incompleteStories = new ArrayList<>();

    public MainpageController(MainpageService mainpageService, StoryMaking storyMaking) {
        this.mainpageService = mainpageService;
        this.storyMaking = storyMaking;
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Object>> main(@RequestBody UserDTO userDTO) {
        Map<String, Object> responseEntity = new HashMap<>();

        // 여기서 페이지 로딩에 필요한 데이터들 다 불러오기
        incompleteStories = mainpageService.findIncompleteStoriesMadeByUser(userDTO.getUsername());
        completeStories = mainpageService.findCompleteStoriesMadeByUser(userDTO.getUsername());

        // 한번에 Map으로 집어넣어서 보내기
        responseEntity.put("incompleteStories", incompleteStories);
        responseEntity.put("completeStories", completeStories);

        return ResponseEntity.ok().body(responseEntity);
    }

}
