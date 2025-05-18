package com.ecommerce.auth.domain;

import com.ecommerce.auth.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class EncryptedAuth {

    private MemberId memberId;
    private EncryptedPassword encryptedPassword;
    private EncryptedAuth(final MemberId memberId,final EncryptedPassword encryptedPassword){
        this.memberId = validate(memberId);
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

    public EncryptedAuth update(final EncryptedAuth request){
        return new EncryptedAuth(request.memberId,request.encryptedPassword);
    }

    public static EncryptedAuth of(final MemberId memberId,final EncryptedPassword password){
        return new EncryptedAuth(memberId,password);
    }
}
