package com.ecommerce.auth;

import com.ecommerce.auth.domain.Auth;
import com.ecommerce.auth.domain.EncryptedAuth;
import com.ecommerce.auth.domain.sessiondata.ValidationSessionData;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.encrypt.Encryptor;
import com.ecommerce.auth.exception.application.DuplicatedRegistrationException;
import com.ecommerce.auth.exception.application.PrevalidationNotCompleteException;
import com.ecommerce.auth.persistence.EncryptedAuthEntity;
import com.ecommerce.auth.persistence.EncryptedAuthEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final EncryptedAuthEntityRepository encryptedAuthEntityRepository;
    private final AccessCodeCreator accessCodeCreator;
    private final Encryptor encryptor;
    @Transactional
    public ValidationSessionDto createValidation(final ValidationSessionDto serverData, final SendVerifyMailRequest request){
        final ValidationSessionData requestValidationData = request.toValidationSessionData(accessCodeCreator);
        final ValidationSessionData serverValidationData = serverData.toValidationSessionData();
        final ValidationSessionData resultValidationSessionData = serverValidationData.substitute(requestValidationData);
        request.getClient().sendMessage(resultValidationSessionData.getValidation());
        return ValidationSessionDto.from(resultValidationSessionData);
    }

    public ValidationSessionDto updateValidation(final ValidationSessionDto serverData, final VerifyAccessCodeRequest request){
        final ValidationSessionData requestValidationSessionData = request.toValidationSessionData();
        final ValidationSessionData serverValidationSessionData = serverData.toValidationSessionData();
        return ValidationSessionDto.from(serverValidationSessionData.update(requestValidationSessionData));
    }

    @Transactional
    public SignUpResponse createAuth(final ValidationSessionDto serverData, final SignUpRequest request){
        final ValidationSessionData serverValidationSessionData = serverData.toValidationSessionData();
        if(!serverValidationSessionData.isComplete()){
            throw new PrevalidationNotCompleteException("사전검증이 완료되지 않았습니다.");
        }
        final Auth requestAuth = request.toAuth();
        final EncryptedAuth requestEncryptedAuth = encryptor.encrypt(requestAuth);
        final EncryptedAuthEntity requestEncryptedAuthEntity = EncryptedAuthEntity.from(requestEncryptedAuth);
        try {
            final EncryptedAuthEntity resultEncryptedAuthEntity = encryptedAuthEntityRepository.save(requestEncryptedAuthEntity);
            final EncryptedAuth resultEncryptedAuth = resultEncryptedAuthEntity.toEncryptedAuth();
            return SignUpResponse.from(resultEncryptedAuth);
        }catch (DataIntegrityViolationException e){
            throw new DuplicatedRegistrationException("이미 존재하는 정보로 회원가입을 진행할수 없습니다.");
        }
    }
}
