package com.ecommerce.auth.port;

import com.ecommerce.auth.domain.sessiondata.validation.Validation;
import com.ecommerce.auth.dto.response.InternalAuthUpdateResponse;
import com.ecommerce.member.MemberReceiver;
import com.ecommerce.member.dto.InternalMemberReadRequest;
import com.ecommerce.member.dto.InternalMemberReadResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateAuthClient {
    private final MemberReceiver memberReceiver;

    public InternalAuthUpdateResponse sendMessage(final Validation validation){
        final InternalMemberReadRequest request = InternalMemberReadRequest.createEmptyMemberId(validation.getEmail().getValue());
        final InternalMemberReadResponse response = memberReceiver.findMemberByEmail(request);
        return InternalAuthUpdateResponse.from(response.getMemberId());
    }

    public static UpdateAuthClient from(final MemberReceiver memberReceiver){
        return new UpdateAuthClient(memberReceiver);
    }
}
