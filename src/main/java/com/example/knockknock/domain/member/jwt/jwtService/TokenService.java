package com.example.knockknock.domain.member.jwt.jwtService;

public interface TokenService  {

    String createAccessToken(String refreshToken) throws Exception;
}
