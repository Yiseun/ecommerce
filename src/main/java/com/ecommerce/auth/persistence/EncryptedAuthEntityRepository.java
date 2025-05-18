package com.ecommerce.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthEntityRepository extends JpaRepository<EncryptedAuthEntity,Long> {

    Optional<EncryptedAuthEntity> findByMemberId(final String memberId);
}
