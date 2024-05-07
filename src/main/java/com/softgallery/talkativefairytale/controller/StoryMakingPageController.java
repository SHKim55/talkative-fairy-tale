package com.softgallery.talkativefairytale.controller;

import static java.lang.Long.parseLong;

import com.softgallery.talkativefairytale.dto.StoryDTO;
import com.softgallery.talkativefairytale.dto.UserDTO;
import com.softgallery.talkativefairytale.service.StoryMaking;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/make")
public class StoryMakingPageController {
    private StoryMaking storyMaking;
    private StoryDTO currentStory, prevStory;

    public StoryMakingPageController(final StoryMaking storyMaking) {
        this.storyMaking = storyMaking;
    }

    @PostMapping("/")
    public ResponseEntity<StoryDTO> makeStory(@RequestBody UserDTO userDTO, @RequestParam(value = "topic") String topic,
                            @RequestParam(value = "level", defaultValue = "1") String level) {
        System.out.println("topic: " + topic + "level: " + level);
        currentStory = new StoryDTO("No Title", userDTO.getUsername(), "", topic, parseLong(level), false, LocalDate.now());
        currentStory = storyMaking.createStory(currentStory);
        return ResponseEntity.ok().body(currentStory);
    }

    @PostMapping("/new/content")
    public ResponseEntity<StoryDTO> addContent(@RequestBody StoryDTO storyInfo) {
        StoryDTO updatedStory = storyMaking.addContentToStory(storyInfo.getId(), storyInfo.getContent());
        return ResponseEntity.ok().body(updatedStory);
    }

//    @PostMapping("/resume")   // <username, id>
//    public ResponseEntity<StoryDTO> resumeStory(@RequestBody Map<String, String> storyInfo) {
//        prevStory = storyMaking.findStoryByUsernameAndId(storyInfo);
//        currentStory = storyMaking.resumeMakingStory(prevStory, "");
//        return ResponseEntity.ok().body(currentStory);
//    }
//
//    @PostMapping("/save")
//    public String saveStory(@Request) {
//
//    }

//    @PostMapping("/save/complete")
}
