package com.ecommerce.auth.ui;

import com.ecommerce.auth.AuthService;
import com.ecommerce.auth.dto.*;
import com.ecommerce.auth.dto.request.*;
import com.ecommerce.auth.dto.response.LoginResponse;
import com.ecommerce.auth.dto.response.SignUpResponse;
import com.ecommerce.auth.ui.session.MemberWriteSession;
import com.ecommerce.auth.ui.session.ValidationSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/mail/send")
    public ResponseEntity<Void> sendVerifyMail(final ValidationSession validationSession, @RequestBody final SendVerifyMailRequest request){
        final ValidationSessionDto response = authService.createValidation(validationSession.getPrevalidationSessionData(), request);
        validationSession.update(response);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/mail/verify")
    public ResponseEntity<Void> verifyAccessCode(final ValidationSession validationSession, @RequestBody final VerifyAccessCodeRequest request){
        final ValidationSessionDto response = authService.updateValidation(validationSession.getPrevalidationSessionData(),request);
        validationSession.update(response);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> createMember(final ValidationSession validationSession, @RequestBody final SignUpRequest request){
        final SignUpResponse response = authService.createAuth(validationSession.getPrevalidationSessionData(),request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetPassword(final ValidationSession validationSession, @RequestBody final UpdateAuthRequest request){
        authService.updateAuth(validationSession.getPrevalidationSessionData(), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/login")
    public ResponseEntity<Void> login(final MemberWriteSession memberWriteSession, @RequestBody final LoginRequest request){
        final LoginResponse response = authService.findAuth(request);
        memberWriteSession.writeSession(response);
        return ResponseEntity.ok().build();
    }
}
