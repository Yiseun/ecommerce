package com.ecommerce.auth.ui.session;

import com.ecommerce.auth.dto.response.LoginResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberWriteSession {

    private static final String KEY = "member";
    private final HttpSession httpSession;

    public void writeSession(final LoginResponse loginResponse){
        httpSession.setAttribute(KEY, loginResponse.getMemberId());
    }

    public static MemberWriteSession from(final HttpSession httpSession){
        return new MemberWriteSession(httpSession);
    }
}
