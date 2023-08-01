package com.example.knockknock.domain.member.jwt.controller;

import com.example.knockknock.domain.member.jwt.dto.CreateAccessTokenRequestDto;
import com.example.knockknock.domain.member.jwt.dto.CreateAccessTokenResponseDto;
import com.example.knockknock.domain.member.jwt.jwtService.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TokenApiController{

    private final TokenService tokenService;

    @PostMapping("api/token")
    public ResponseEntity<CreateAccessTokenResponseDto> createAccessTokenResponseDtoRequestEntity
            (@RequestBody CreateAccessTokenRequestDto createAccessTokenRequestDto) throws Exception {
        String newAccessToken = tokenService.createAccessToken(createAccessTokenRequestDto.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponseDto(newAccessToken));
    }
}
