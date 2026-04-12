package com.ecommerce.notification.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SendMessageRequest {
    private final String topicId;
    private final String title;
    private final String body;
}
