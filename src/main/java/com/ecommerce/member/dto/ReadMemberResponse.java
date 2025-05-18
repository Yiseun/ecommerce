package com.ecommerce.member.dto;

import com.ecommerce.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class ReadMemberResponse {
    private final String memberId;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final String postcode;

    public static ReadMemberResponse from(final Member member){
        return ReadMemberResponse.builder().memberId(member.getMemberId().getValue())
                .email(member.getEmail().getValue())
                .phoneNumber(member.getPhoneNumber().getValue())
                .address(member.getAddress().getValue())
                .postcode(member.getPostcode().getValue())
                .build();
    }
}
