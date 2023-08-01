package com.example.knockknock.domain.member.jwt.jwtService;

import com.example.knockknock.domain.member.jwt.jwtentity.RefreshToke;

public interface RefreshTokenService {

    RefreshToke findByRefreshTokenId(String refreshToken) throws Exception;
}
