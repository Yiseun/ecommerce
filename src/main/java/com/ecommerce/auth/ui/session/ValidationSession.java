package com.ecommerce.auth.ui.session;

import com.ecommerce.auth.dto.ValidationSessionDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidationSession {
    private static final String KEY = "prevalidation";
    private final HttpSession httpSession;

    public void update(final ValidationSessionDto validationSessionDto){
        httpSession.setAttribute(KEY, validationSessionDto);
    }
    public ValidationSessionDto getPrevalidationSessionData(){
        final ValidationSessionDto validationSessionDto = (ValidationSessionDto) httpSession.getAttribute(KEY);
        if(validationSessionDto ==null){
            return ValidationSessionDto.init();
        }
        return validationSessionDto;
    }

    public static ValidationSession from(final HttpSession httpSession){
        return new ValidationSession(httpSession);
    }
}
