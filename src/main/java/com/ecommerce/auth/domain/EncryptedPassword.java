package com.ecommerce.auth.domain;

import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.regex.Pattern;

@EqualsAndHashCode
@Getter
public class EncryptedPassword {
    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
    private final String value;

    private EncryptedPassword(final String value){
        this.value = validate(value);
    }
    private String validate(final String value){
        if(value==null||!BCRYPT_PATTERN.matcher(value).matches()){
            throw new InvalidConstructionException("비밀번호가 검증조건과 일치하는 방식으로 암호화되지 않았습니다");
        }
        return value;
    }
    public static EncryptedPassword from(final String value){
        return new EncryptedPassword(value);
    }
}
