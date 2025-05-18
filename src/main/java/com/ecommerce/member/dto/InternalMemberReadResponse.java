package com.ecommerce.member.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
public class InternalMemberReadResponse {
    private final String memberId;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final String postcode;
}