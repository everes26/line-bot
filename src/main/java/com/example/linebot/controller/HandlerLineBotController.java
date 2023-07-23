package com.example.linebot.controller;

import com.example.linebot.model.Event;
import com.example.linebot.model.Message;
import com.example.linebot.model.ReplyMessage;
import com.example.linebot.model.WebhookEvent;
import com.example.linebot.service.LineBotService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static com.example.linebot.util.StringUtil.WEBHOOK_EVENT_TYPE_FOLLOW;

@Slf4j
@RestController
public class HandlerLineBotController {

    private final LineBotService lineBotService;

    public HandlerLineBotController(LineBotService lineBotService) {
        this.lineBotService = lineBotService;
    }

    @PostMapping("/")
    public ResponseEntity<HttpStatus> handlerWebhook(@RequestBody WebhookEvent request) {
        log.info("Webhook event: " + request);
        List<Event> events = request.getEvents();
        if (events.size() > 0) {
            events.forEach( event -> {
                log.info("Event: " + event);
                if (WEBHOOK_EVENT_TYPE_FOLLOW.equals(event.getType())) {
                    ReplyMessage replyMessage = ReplyMessage.builder()
                            .replyToken(event.getReplyToken())
                            .messages(Collections.singletonList(Message.builder()
                                    .type("text")
                                    .text("Cảm ơn bạn đã follow tôi!")
                                    .build())).build();
                    try {
                        lineBotService.sendReplyMessage(replyMessage);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        return ResponseEntity.ok().build();
    }
}
