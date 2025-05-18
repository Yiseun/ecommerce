package com.ecommerce.member.dto;

import com.ecommerce.member.persistence.MemberEntity;
import org.springframework.data.jpa.domain.Specification;

public interface ReadMemberRequest {
    Specification<MemberEntity> toSpecification();
}
