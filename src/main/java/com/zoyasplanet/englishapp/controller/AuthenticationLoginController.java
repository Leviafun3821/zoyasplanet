package com.zoyasplanet.englishapp.controller;

import com.zoyasplanet.englishapp.authentication.service.AuthenticationService;
import com.zoyasplanet.englishapp.dto.auth.JwtResponse;
import com.zoyasplanet.englishapp.dto.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationLoginController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authenticationService.authenticateAndGenerateToken(
                loginRequest.username(), loginRequest.password()
        );
        return ResponseEntity.ok(new JwtResponse(token));
    }

}
