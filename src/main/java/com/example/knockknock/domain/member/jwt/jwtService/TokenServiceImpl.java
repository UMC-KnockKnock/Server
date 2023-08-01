package com.example.knockknock.domain.member.jwt.jwtService;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.jwt.TokenProvider;
import com.example.knockknock.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final MemberService memberService;

    private final RefreshTokenService refreshTokenService;

    private final TokenProvider tokenProvider;

    @Override
    public String createAccessToken(String refreshToken) throws Exception {

        if(!tokenProvider.vliToken(refreshToken)){
            throw new IllegalArgumentException("토큰 만료되었습니다");
        }

        Long memberId = refreshTokenService.findByRefreshTokenId(refreshToken).getId();

        Member member = memberService.findByMemberId(memberId);

        return tokenProvider.generateToken(member, Duration.ofHours(2));

    }
}
