package com.ecommerce.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class InternalMemberReadRequest {
    private final String memberId;
    private final String email;
}
