package com.ecommerce.member.dto;

import com.ecommerce.member.domain.Email;
import com.ecommerce.member.persistence.MemberEntity;
import com.ecommerce.member.persistence.MemberSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@RequiredArgsConstructor
public class ReadMemberByEmailRequest implements ReadMemberRequest{
    private final String email;
    @Override
    public Specification<MemberEntity> toSpecification() {
        final Email email = Email.from(this.email);
        return MemberSpecifications.findByEmailSpec(email);
    }

    public static ReadMemberByEmailRequest from(final String email){
        return new ReadMemberByEmailRequest(email);
    }
}
