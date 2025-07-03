package com.ecommerce.grobal.session;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRequest {
    private final String memberId;

    public static MemberRequest from(final String memberId){
        return new MemberRequest(memberId);
    }
}
