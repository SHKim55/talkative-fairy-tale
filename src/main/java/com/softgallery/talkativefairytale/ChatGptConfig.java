package com.softgallery.talkativefairytale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:openai.properties")
public class ChatGptConfig {

    public static String API_KEY;

    @Value("${chatgpt.api-key}")
    public void setSecretKey(String value) {
        API_KEY = value;
    }

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";
    public static final String MODEL = "gpt-3.5-turbo-instruct";
    public static final Integer MAX_TOKEN = 300;
    public static final Double TEMPERATURE = 0.0;
    public static final Double TOP_P = 1.0;
    public static final String MEDIA_TYPE = "application/json; charset=UTF-8";
    public static final String URL = "https://api.openai.com/v1/completions";
}