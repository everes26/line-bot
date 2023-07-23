package com.example.linebot.model;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
public class DeliveryContext {
    private boolean isRedelivery;
}
