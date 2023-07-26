package com.example.linebot.controller;

import com.example.linebot.model.Event;
import com.example.linebot.model.Message;
import com.example.linebot.model.Profile;
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


                    try {
                        Profile profile = lineBotService.getProfile(event.getSource().getUserId());
                        Message message1 = Message.builder()
                                .type("text")
                                .text("Tin nhắn này được gửi khi bạn follow chúng tôi!!!")
                                .build();
                        Message message2 = Message.builder()
                                .type("text")
                                .text(String.format("Cảm ơn %s đã follow chúng tôi!!!", profile.getDisplayName()))
                                .build();
                        Message message3 = Message.builder()
                                .type("text")
                                .text("Chúng tôi đã lấy được thông tin của bạn:")
                                .build();
                        Message message4 = Message.builder()
                                .type("text")
                                .text(String.format("Tên bạn là: %s \nNgôn ngữ: %s \nTrạng thái: %s \nUrl ảnh đại diện: %s",
                                        profile.getDisplayName(), profile.getLanguage(), profile.getStatusMessage(), profile.getPictureUrl()))
                                .build();
                        Message message5 = Message.builder()
                                .type("text")
                                .text(String.format("Chúc %s một ngày tốt lành!!!", profile.getDisplayName()))
                                .build();
                        ReplyMessage replyMessage = ReplyMessage.builder()
                                .replyToken(event.getReplyToken())
                                .messages(List.of(message1, message2, message3, message4, message5)).build();
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
