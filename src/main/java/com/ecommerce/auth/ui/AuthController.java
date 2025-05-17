package com.ecommerce.auth.ui;

import com.ecommerce.auth.AuthService;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.port.AuthClientRegistry;
import com.ecommerce.auth.ui.session.ValidationSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthClientRegistry authClientRegistry;
    private final AuthService authService;
    @PostMapping("/mail/send")
    public ResponseEntity<Void> sendVerifyMail(final ValidationSession validationSession, @RequestBody final SendVerifyMailRequestBody body){
        final ValidationSessionDto response = authService.createValidation(validationSession.getPrevalidationSessionData(), SendVerifyMailRequest.of(authClientRegistry.getCreatePrevalidationClient(),body));
        validationSession.update(response);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/mail/verify")
    public ResponseEntity<Void> verifyAccessCode(final ValidationSession validationSession, @RequestBody final VerifyAccessCodeRequest request){
        final ValidationSessionDto response = authService.updateValidation(validationSession.getPrevalidationSessionData(),request);
        validationSession.update(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/member/sign-up")
    public ResponseEntity<SignUpResponse> createMember(final ValidationSession validationSession, @RequestBody final SignUpRequest request){
        final SignUpResponse response = authService.createAuth(validationSession.getPrevalidationSessionData(),request);
        return ResponseEntity.ok(response);
    }
}
