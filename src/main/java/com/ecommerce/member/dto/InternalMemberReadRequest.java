package com.ecommerce.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InternalMemberReadRequest {
    private final String memberId;
    private final String email;

    public static InternalMemberReadRequest createEmptyMemberId(final String email){
        return new InternalMemberReadRequest(null, email);
    }
}
