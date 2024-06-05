package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.dto.StoryNumberDTO;
import com.softgallery.talkativefairytale.service.CommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@ResponseBody
@RequestMapping("/community")
public class CommunityController {
    private final CommunityService communityService;

    public CommunityController(CommunityService communityService) {
        this.communityService = communityService;
    }

    @GetMapping("/ranking")
    public StoryNumberDTO getRanker() {
        return communityService.getRanker();
    }

    //topic: all, DB에 있는 topic, type: recent, like
    @GetMapping("/stories/{topic}/{type}")
    public StoryNumberDTO getRanker(@PathVariable("topic") String topic, @PathVariable("type") String type) {
        return communityService.getBy(topic, type);
    }

    @GetMapping("/topics")
    public Set<String> getAllTopics() {
        return communityService.getAllTopics();
    }

    //1: like / 0: unlike
    @GetMapping("/evaluation/{storyId}/{likeOrUnlike}")
    public StoryDTO addEvaluation(@PathVariable("storyId") Long storyID, @PathVariable("likeOrUnlike") Long likeOrUnlike,
                                  @RequestHeader("Authorization") String token) {
        return communityService.addEvaluation(likeOrUnlike, storyID, token);
    }
}
