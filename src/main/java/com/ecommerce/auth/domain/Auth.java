package com.ecommerce.auth.domain;

import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
public class Auth {
    @EqualsAndHashCode.Include
    private MemberId memberId;
    @EqualsAndHashCode.Include
    private EncryptedPassword encryptedPassword;
    private Email email;
    private Auth(final MemberId memberId,final Email email,final EncryptedPassword encryptedPassword){
        this.memberId = validate(memberId);
        this.email = email;
        this.encryptedPassword = validate(encryptedPassword);
    }
    private MemberId validate(final MemberId memberId){
        if(memberId==null){
            throw new InvalidConstructionException("memberId는 필수입력값입니다.");
        }
        return memberId;
    }

    private EncryptedPassword validate(final EncryptedPassword password){
        if(password==null){
            throw new InvalidConstructionException("password는 필수입력값입니다.");
        }
        return password;
    }

    public static Auth of(final MemberId memberId,final Email email,final EncryptedPassword password){
        return new Auth(memberId,email,password);
    }
}
