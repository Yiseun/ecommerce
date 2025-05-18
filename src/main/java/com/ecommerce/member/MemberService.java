package com.ecommerce.member;

import com.ecommerce.member.domain.Member;
import com.ecommerce.member.dto.ReadMemberRequest;
import com.ecommerce.member.dto.ReadMemberResponse;
import com.ecommerce.member.exception.application.MemberNotFoundException;
import com.ecommerce.member.persistence.MemberEntity;
import com.ecommerce.member.persistence.MemberEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberEntityRepository memberEntityRepository;

    public ReadMemberResponse findMember(final ReadMemberRequest request){
        final Specification<MemberEntity> specification = request.toSpecification();
        final MemberEntity serverMemberEntity = memberEntityRepository.findOne(specification).orElseThrow(()->new MemberNotFoundException("일치하는 회원정보를 찾을수 없습니다."));
        final Member serverMember = serverMemberEntity.toMember();
        return ReadMemberResponse.from(serverMember);
    }
}
