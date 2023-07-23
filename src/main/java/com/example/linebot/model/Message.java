package com.example.linebot.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class Message {
    private String type;
    private String id;
    private String text;
}
