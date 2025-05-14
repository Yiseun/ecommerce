package com.ecommerce.auth.ui.session;

import com.ecommerce.auth.dto.PrevalidationSessionDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrevalidationSession {
    private static final String KEY = "prevalidation";
    private final HttpSession httpSession;

    public void update(final PrevalidationSessionDto prevalidationSessionDto){
        httpSession.setAttribute(KEY,prevalidationSessionDto);
    }
    public PrevalidationSessionDto getPrevalidationSessionData(){
        final PrevalidationSessionDto prevalidationSessionDto = (PrevalidationSessionDto) httpSession.getAttribute(KEY);
        if(prevalidationSessionDto==null){
            return PrevalidationSessionDto.init();
        }
        return prevalidationSessionDto;
    }

    public static PrevalidationSession from(final HttpSession httpSession){
        return new PrevalidationSession(httpSession);
    }
}
