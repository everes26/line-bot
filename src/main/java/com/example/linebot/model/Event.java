package com.example.linebot.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class Event {
    private String type;
    private Message message;
    private long timestamp;
    private Source source;
    private String replyToken;
    private String mode;
    private String webhookEventId;
    private DeliveryContext deliveryContext;
}
