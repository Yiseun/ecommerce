package com.ecommerce.grobal.session;

import com.ecommerce.grobal.exception.NotAuthorizedException;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberReadSession {
    private static final String KEY = "member";
    private final HttpSession httpSession;

    public MemberRequest getMemberSessionData(){
        final String sessionInfo = (String) httpSession.getAttribute(KEY);
        if(sessionInfo ==null){
            throw new NotAuthorizedException("로그인이 필요합니다.");
        }
        return MemberRequest.from(sessionInfo);
    }

    public static MemberReadSession from(final HttpSession httpSession){
        return new MemberReadSession(httpSession);
    }
}
