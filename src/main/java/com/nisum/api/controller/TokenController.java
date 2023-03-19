package com.nisum.api.controller;

import com.nisum.api.dto.response.TokenResponse;
import com.nisum.api.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @GetMapping
    public TokenResponse getToken() {
        return new TokenResponse(tokenService.generateTokenBySecurityType("test@test.cl"));
    }
}
