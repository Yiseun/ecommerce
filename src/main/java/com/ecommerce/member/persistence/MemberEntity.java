package com.ecommerce.member.persistence;

import com.ecommerce.member.domain.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MemberEntity {
    @Id
    private Long memberEntityId;
    @Column(unique = true)
    private String memberId;
    @Column(unique = true)
    private String email;
    private String phoneNumber;
    private String address;
    private String postcode;

    public Member toMember(){
        final MemberId memberId = MemberId.from(this.memberId);
        final Email email = Email.from(this.email);
        final PhoneNumber phoneNumber = PhoneNumber.from(this.phoneNumber);
        final Address address = Address.from(this.address);
        final Postcode postcode = Postcode.from(this.postcode);
        return Member.of(memberId,email,phoneNumber,address,postcode);
    }
}
