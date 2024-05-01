package com.softgallery.talkativefairytale.controller;

import com.softgallery.talkativefairytale.service.MainpageService;
import org.springframework.stereotype.Controller;

@Controller
public class MainpageController {
    private final MainpageService mainpageService;

    public MainpageController(MainpageService mainpageService) {
        this.mainpageService = mainpageService;
    }

}
