package com.ecommerce.auth.persistence;

import com.ecommerce.auth.domain.Auth;
import com.ecommerce.auth.domain.sessiondata.validation.Email;
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
public class AuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Column(unique = true)
    private String memberId;
    private String password;

    public Auth toAuth(){
        final MemberId memberId = MemberId.from(this.memberId);
        final EncryptedPassword password = EncryptedPassword.from(this.password);
        return Auth.of(memberId,password);
    }

    public static AuthEntity from(final Auth auth){
        final String memberId = auth.getMemberId().getValue();
        final String password = auth.getEncryptedPassword().getValue();
        return new AuthEntity(null, memberId, password);
    }
}
