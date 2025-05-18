package com.ecommerce.member.domain;

import com.ecommerce.member.exception.application.domain.InvalidConstructionException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberId {
    private final String value;

    public static MemberId from(final String value){
        if(value==null){
            throw new InvalidConstructionException("memberId는 null일수 없습니다.");
        }
        return new MemberId(value);
    }
}
