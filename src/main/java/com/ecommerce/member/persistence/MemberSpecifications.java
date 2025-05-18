package com.ecommerce.member.persistence;

import com.ecommerce.member.domain.Email;
import com.ecommerce.member.domain.MemberId;
import org.springframework.data.jpa.domain.Specification;

public class MemberSpecifications {
    public static Specification<MemberEntity> findByMemberIdSpec(final MemberId memberId){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("memberId"),memberId.getValue());
    }
    public static Specification<MemberEntity> findByEmailSpec(final Email email){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("email"),email.getValue());
    }
}
