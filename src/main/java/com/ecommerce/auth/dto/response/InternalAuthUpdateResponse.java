package com.ecommerce.auth.dto.response;

import com.ecommerce.auth.domain.Auth;
import com.ecommerce.auth.domain.MemberId;
import com.ecommerce.auth.domain.RawPassword;
import com.ecommerce.auth.dto.request.UpdateAuthRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class InternalAuthUpdateResponse {
    private final String memberId;

    public Auth toAuth(final UpdateAuthRequest request){
        final MemberId memberId = MemberId.from(this.memberId);
        final RawPassword rawPassword = request.toPassword();
        return Auth.of(memberId,rawPassword);
    }

    public static InternalAuthUpdateResponse from(final String memberId){
        return new InternalAuthUpdateResponse(memberId);
    }
}
