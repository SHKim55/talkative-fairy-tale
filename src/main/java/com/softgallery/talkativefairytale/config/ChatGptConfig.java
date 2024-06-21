package com.softgallery.talkativefairytale.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:openai.properties")
public class ChatGptConfig {

    public static String API_KEY;

    @Value("${chatgpt.api-key}")
    public void setSecretKey(String key_value) {
        API_KEY = key_value;
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String MODEL = "gpt-4o";
    public static final Integer MAX_TOKEN = 4000;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; c`harset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/chat/completions";
}