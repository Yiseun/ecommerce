package com.ecommerce.auth;

import com.ecommerce.auth.domain.Auth;
import com.ecommerce.auth.domain.sessiondata.PrevalidationSessionData;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.encrypt.Encryptor;
import com.ecommerce.auth.exception.application.DuplicatedRegistrationException;
import com.ecommerce.auth.exception.application.PrevalidationNotCompleteException;
import com.ecommerce.auth.persistence.AuthEntity;
import com.ecommerce.auth.persistence.AuthEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final AuthEntityRepository authEntityRepository;
    private final AccessCodeCreator accessCodeCreator;
    private final Encryptor encryptor;
    @Transactional
    public PrevalidationSessionDto createPrevalidation(final PrevalidationSessionDto serverData, final SendVerifyMailRequest request){
        final PrevalidationSessionData requestPrevalidationData = request.toPrevalidationSessionData(accessCodeCreator);
        final PrevalidationSessionData serverPrevalidationData = serverData.toPrevalidationSessionData();
        final PrevalidationSessionData resultPrevalidationSessionData = serverPrevalidationData.substitute(requestPrevalidationData);
        request.getClient().sendMessage(resultPrevalidationSessionData.getPrevalidation());
        return PrevalidationSessionDto.from(resultPrevalidationSessionData);
    }

    public PrevalidationSessionDto updatePrevalidation(final PrevalidationSessionDto serverData, final VerifyAccessCodeRequest request){
        final PrevalidationSessionData requestPrevalidationSessionData = request.toPrevalidationSessionData();
        final PrevalidationSessionData serverPrevalidationSessionData = serverData.toPrevalidationSessionData();
        return PrevalidationSessionDto.from(serverPrevalidationSessionData.update(requestPrevalidationSessionData));
    }

    @Transactional
    public SignUpResponse createAuth(final PrevalidationSessionDto serverData, final SignUpRequest request){
        final PrevalidationSessionData serverPrevalidationSessionData = serverData.toPrevalidationSessionData();
        if(!serverPrevalidationSessionData.isComplete()){
            throw new PrevalidationNotCompleteException("사전검증이 완료되지 않았습니다.");
        }
        final Auth requestAuth = request.toAuth(serverData,encryptor);
        final AuthEntity requestAuthEntity = AuthEntity.from(requestAuth);
        try {
            final AuthEntity resultAuthEntity = authEntityRepository.save(requestAuthEntity);
            final Auth resultAuth = resultAuthEntity.toAuth();
            return SignUpResponse.from(resultAuth);
        }catch (DataIntegrityViolationException e){
            throw new DuplicatedRegistrationException("이미 존재하는 정보로 회원가입을 진행할수 없습니다.");
        }
    }
}
