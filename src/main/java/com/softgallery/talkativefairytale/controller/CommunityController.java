package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.service.CommunityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/community")
public class CommunityController {
    public CommunityService communityService;


}
