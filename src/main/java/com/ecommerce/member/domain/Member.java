package com.ecommerce.member.domain;

import com.ecommerce.member.exception.application.domain.InvalidConstructionException;
import lombok.Getter;

@Getter
public class Member {
    private final MemberId memberId;
    private final Email email;
    private final PhoneNumber phoneNumber;
    private final Address address;
    private final Postcode postcode;

    private Member(final MemberId memberId,
                   final Email email,
                   final PhoneNumber phoneNumber,
                   final Address address,
                   final Postcode postcode){
        this.memberId = validate(memberId);
        this.email = validate(email);
        this.phoneNumber = validate(phoneNumber);
        this.address = validate(address);
        this.postcode = validate(postcode);
    }

    private MemberId validate(final MemberId memberId){
        if(memberId==null){
            throw new InvalidConstructionException("memberId는 null일수 없습니다.");
        }
        return memberId;
    }
    private Email validate(final Email email){
        if(email==null){
            throw new InvalidConstructionException("email은 null일수 없습니다.");
        }
        return email;
    }
    private PhoneNumber validate(final PhoneNumber phoneNumber){
        if(phoneNumber==null){
            throw new InvalidConstructionException("phoneNumber는 null일수 없습니다.");
        }
        return phoneNumber;
    }
    private Address validate(final Address address){
        if(address==null){
            throw new InvalidConstructionException("address는 null일수 없습니다.");
        }
        return address;
    }
    private Postcode validate(final Postcode postcode){
        if(postcode==null){
            throw new InvalidConstructionException("postcode는 null일수 없습니다.");
        }
        return postcode;
    }

    public static Member of(final MemberId memberId,
                            final Email email,
                            final PhoneNumber phoneNumber,
                            final Address address,
                            final Postcode postcode){
        return new Member(memberId, email, phoneNumber, address, postcode);
    }
}
