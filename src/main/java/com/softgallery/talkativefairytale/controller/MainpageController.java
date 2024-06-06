package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.service.story.MainpageService;
import com.softgallery.talkativefairytale.service.story.StoryMakingService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main")
public class MainpageController {
    private final MainpageService mainpageService;

    public MainpageController(final MainpageService mainpageService) {
        this.mainpageService = mainpageService;
    }

    @GetMapping("/list/incomplete")
    public ResponseEntity<List<StoryDTO>> loadIncompleteStories(@RequestHeader(name = "Authorization") String userToken) {
        List<StoryDTO> incompleteStories = mainpageService.findIncompleteStoriesMadeByUserName(userToken);

        return ResponseEntity.ok().body(incompleteStories);
    }

    @GetMapping("/list/complete")
    public ResponseEntity<List<StoryDTO>> loadCompleteStories(@RequestHeader(name = "Authorization") String userToken) {
        List<StoryDTO> completeStories = mainpageService.findCompleteStoriesMadeByUserName(userToken);

        return ResponseEntity.ok().body(completeStories);
    }
}
