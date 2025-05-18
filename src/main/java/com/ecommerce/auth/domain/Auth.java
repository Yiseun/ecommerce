package com.ecommerce.auth.domain;

import com.ecommerce.member.exception.application.domain.InvalidConstructionException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class Auth {
    private final MemberId memberId;
    private final RawPassword rawPassword;

    private Auth(final MemberId memberId,final RawPassword rawPassword){
        this.memberId = validate(memberId);
        this.rawPassword = validate(rawPassword);
    }

    public MemberId validate(final MemberId memberId){
        if(memberId==null){
            throw new InvalidConstructionException("memberId는 null일수 없습니다.");
        }
        return memberId;
    }
    public RawPassword validate(final RawPassword rawPassword){
        if(rawPassword==null){
            throw new InvalidConstructionException("password는 null일수 없습니다.");
        }
        return rawPassword;
    }

    public static Auth of(final MemberId memberId,final RawPassword rawPassword){
        return new Auth(memberId, rawPassword);
    }
}
