package com.example.linebot.controller;

import com.example.linebot.model.Message;
import com.example.linebot.model.PushMessage;
import com.example.linebot.model.WebhookEvent;
import com.example.linebot.service.LineBotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@Slf4j
@RestController
public class LineBotController {

    private final LineBotService lineBotService;

    public LineBotController(LineBotService lineBotService) {
        this.lineBotService = lineBotService;
    }

    @PostMapping("/message/push")
    public ResponseEntity<Object> sendPushMessage(@RequestParam String to, @RequestParam String text) throws JsonProcessingException {
        PushMessage pushMessage = PushMessage.builder()
                .to(to)
                .messages(Collections.singletonList(Message.builder()
                        .type("text")
                        .text(text).build())).build();
        return ResponseEntity.ok(lineBotService.sendPushMessage(pushMessage));
    }


}
