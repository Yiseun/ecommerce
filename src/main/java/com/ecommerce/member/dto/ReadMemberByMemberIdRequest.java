package com.ecommerce.member.dto;

import com.ecommerce.member.domain.MemberId;
import com.ecommerce.member.persistence.MemberEntity;
import com.ecommerce.member.persistence.MemberSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ReadMemberByMemberIdRequest implements ReadMemberRequest{
    private final String memberId;
    @Override
    public Specification<MemberEntity> toSpecification() {
        final MemberId memberId = MemberId.from(this.memberId);
        return MemberSpecifications.findByMemberIdSpec(memberId);
    }

    public static ReadMemberByMemberIdRequest from(final String memberId){
        return new ReadMemberByMemberIdRequest(memberId);
    }
}
