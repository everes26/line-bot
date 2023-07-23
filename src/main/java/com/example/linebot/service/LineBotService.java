package com.example.linebot.service;

import com.example.linebot.client.LineBotClient;
import com.example.linebot.model.PushMessage;
import com.example.linebot.model.ReplyMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class LineBotService {

    private final LineBotClient lineBotClient;

    @Value("${url.line.message.push}")
    private String LINE_MESSAGE_PUSH;

    @Value("${url.line.message.reply}")
    private String LINE_MESSAGE_REPLY;

    public LineBotService(LineBotClient lineBotClient) {
        this.lineBotClient = lineBotClient;
    }

    public Object sendPushMessage(PushMessage pushMessage) throws JsonProcessingException {
        return lineBotClient.callMessagingAPI(pushMessage, LINE_MESSAGE_PUSH);
    }

    public Object sendReplyMessage(ReplyMessage replyMessage) throws JsonProcessingException {
        return lineBotClient.callMessagingAPI(replyMessage, LINE_MESSAGE_REPLY);
    }
}
