package com.example.knockknock.domain.member.jwt.jwtService;

import com.example.knockknock.domain.member.jwt.jwtRepository.RefreshTokenRepository;
import com.example.knockknock.domain.member.jwt.jwtentity.RefreshToke;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToke findByRefreshTokenId(String refreshToken) throws Exception {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new IllegalArgumentException("토큰이 없습니다"));

    }
}
