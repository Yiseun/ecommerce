package com.ecommerce.notification.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UnsubscribeRequest {
    private final String memberId;
    private final String token;
    private final String topicId;
}
