package com.ecommerce.auth.domain;

import com.ecommerce.auth.exception.application.domain.InvalidUserInputException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

@EqualsAndHashCode
@Getter
public class RawPassword {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()_+])(?=.*[^\\s]).{8,}$");
    private final String value;
    private RawPassword(final String value){
        this.value = validate(value);
    }
    private String validate(final String value){
        if(value==null||!PASSWORD_PATTERN.matcher(value).matches()){
            throw new InvalidUserInputException("비밀번호를 양식에 맞춰 다시 입력해주세요");
        }
        return value;
    }

    public static RawPassword from(final String value){
        return new RawPassword(value);
    }
}
