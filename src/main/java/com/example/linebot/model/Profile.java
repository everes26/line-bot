package com.example.linebot.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class Profile {
    private String displayName;
    private String userId;
    private String language;
    private String pictureUrl;
    private String statusMessage;
}
