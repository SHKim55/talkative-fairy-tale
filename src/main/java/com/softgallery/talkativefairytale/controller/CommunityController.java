package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.dto.StoryInfoDTO;
import com.softgallery.talkativefairytale.dto.StoryNumberDTO;
import com.softgallery.talkativefairytale.service.community.CommunityService;
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
    public StoryNumberDTO getRanker(@RequestHeader("Authorization") String token) {
        return communityService.getRanker(token);
    }

    //topic: all, DB에 있는 topic, type: recent, like
    @GetMapping("/stories/{topic}/{type}")
    public StoryNumberDTO getBy(@PathVariable("topic") String topic, @PathVariable("type") String type,
                                @RequestHeader("Authorization") String token) {
        return communityService.getBy(topic, type, token);
    }

    @GetMapping("/topics")
    public Set<String> getAllTopics() {
        return communityService.getAllTopics();
    }

    //1: like / 0: unlike
    @GetMapping("/evaluation/{storyId}/{likeOrUnlike}")
    public StoryInfoDTO addEvaluation(@PathVariable("storyId") Long storyID, @PathVariable("likeOrUnlike") Long likeOrUnlike,
                                      @RequestHeader("Authorization") String token) {
        return communityService.addEvaluation(likeOrUnlike, storyID, token);
    }
}
