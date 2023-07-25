package com.example.linebot.client;

import com.example.linebot.model.Profile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.example.linebot.util.StringUtil.BODY_NULL_FORMAT_JOSN;

@Slf4j
@Component
public class LineBotClient {

    @Value("${channel.access.token}")
    private String CHANNEL_ACCESS_TOKEN;

    public Object callPostAPI(Object body, String url) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", "Bearer " + CHANNEL_ACCESS_TOKEN);
        String jsonBody = Objects.isNull(body) ? BODY_NULL_FORMAT_JOSN : new ObjectMapper().writeValueAsString(body);

        HttpEntity<String> request = new HttpEntity<>(jsonBody, headers);
        Object result = restTemplate.postForObject(url, request, String.class);
        log.info("----------CALL EXTERNAL API START----------");
        log.info("Path URL: " + url);
        log.info("Request: " + request);
        log.info("Result: " + result);
        log.info("----------CALL EXTERNAL API END----------");
        return result;
    }

    public Profile callGetAPI(String url) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + CHANNEL_ACCESS_TOKEN);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Profile> response = restTemplate.exchange(url, HttpMethod.GET, entity, Profile.class);

        log.info("----------CALL EXTERNAL API START----------");
        log.info("Path URL: " + url);
        log.info("Request: " + entity);
        log.info("Result: " + response);
        log.info("----------CALL EXTERNAL API END----------");
        return response.getBody();
    }
}
