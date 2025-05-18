package com.ecommerce.auth.persistence;

import com.ecommerce.auth.domain.EncryptedAuth;
import com.ecommerce.auth.domain.EncryptedPassword;
import com.ecommerce.auth.domain.MemberId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EncryptedAuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Column(unique = true)
    private String memberId;
    private String encryptedPassword;

    public EncryptedAuth toEncryptedAuth(){
        final MemberId memberId = MemberId.from(this.memberId);
        final EncryptedPassword password = EncryptedPassword.from(this.encryptedPassword);
        return EncryptedAuth.of(memberId,password);
    }

    public static EncryptedAuthEntity from(final EncryptedAuth auth){
        final String memberId = auth.getMemberId().getValue();
        final String password = auth.getEncryptedPassword().getValue();
        return new EncryptedAuthEntity(null, memberId, password);
    }
}
