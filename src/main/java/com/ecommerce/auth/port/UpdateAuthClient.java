package com.ecommerce.auth.port;

import com.ecommerce.auth.domain.sessiondata.ValidationSessionData;
import com.ecommerce.auth.dto.response.InternalAuthUpdateResponse;
import com.ecommerce.member.MemberReceiver;
import com.ecommerce.member.dto.InternalMemberReadRequest;
import com.ecommerce.member.dto.InternalMemberReadResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAuthClient {
    private final MemberReceiver memberReceiver;

    public InternalAuthUpdateResponse sendMessage(final ValidationSessionData validationSessionData){
        final InternalMemberReadRequest request = InternalMemberReadRequest.of(null,validationSessionData.getValidation().getEmail().getValue());
        final InternalMemberReadResponse response = memberReceiver.findMemberByEmail(request);
        return InternalAuthUpdateResponse.from(response.getMemberId());
    }

    public static UpdateAuthClient from(final MemberReceiver memberReceiver){
        return new UpdateAuthClient(memberReceiver);
    }
}
