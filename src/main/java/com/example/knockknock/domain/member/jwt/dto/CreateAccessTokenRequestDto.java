package com.example.knockknock.domain.member.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAccessTokenRequestDto {

    private String refreshToken;
}
