package com.ecommerce.member;

import com.ecommerce.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberReceiver {
    private final MemberService memberService;
    public InternalMemberReadResponse findMemberByMemberId(final InternalMemberReadRequest internalRequest){
        final ReadMemberByMemberIdRequest request = ReadMemberByMemberIdRequest.from(internalRequest.getMemberId());
        final ReadMemberResponse response = memberService.findMember(request);
        return InternalMemberReadResponse.builder()
                .memberId(response.getMemberId())
                .email(response.getEmail())
                .phoneNumber(response.getPhoneNumber())
                .address(response.getAddress())
                .postcode(response.getPostcode())
                .build();
    }

    public InternalMemberReadResponse findMemberByEmail(final InternalMemberReadRequest internalRequest){
        final ReadMemberByEmailRequest request = ReadMemberByEmailRequest.from(internalRequest.getEmail());
        final ReadMemberResponse response = memberService.findMember(request);
        return InternalMemberReadResponse.builder()
                .memberId(response.getMemberId())
                .email(response.getEmail())
                .phoneNumber(response.getPhoneNumber())
                .address(response.getAddress())
                .postcode(response.getPostcode())
                .build();
    }
}
