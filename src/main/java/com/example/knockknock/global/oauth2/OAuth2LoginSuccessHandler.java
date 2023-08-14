package com.example.knockknock.global.oauth2;

import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.security.UserDetailsServiceImpl;
import com.example.knockknock.global.jwt.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService;

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            log.info("OAuth2 Login 성공!");
            // OAuth2 로그인 성공 후 처리


            // OAuth2User를 얻는다.
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            // DB 등에서 UserDetails를 로드한다. (이미 구현한 서비스를 사용)
            UserDetails userDetails = userDetailsService.loadUserByUsername(oAuth2User.getEmail());

            // JWT 토큰 생성
            String accessToken = jwtUtil.createAccessToken(userDetails.getUsername());
            String refreshToken = jwtUtil.createRefreshToken(userDetails.getUsername());

            // 응답 헤더에 토큰 추가
            response.addHeader(jwtUtil.ACCESS_TOKEN_HEADER, accessToken);
            response.addHeader(jwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
            log.info("JWT 토큰이 성공적으로 생성되었습니다. Access Token: {}, Refresh Token: {}", accessToken, refreshToken);
        } catch (Exception e) {
            log.error("OAuth2 로그인 또는 토큰 생성 중 오류가 발생했습니다.", e);
            throw new ServletException("OAuth2 로그인 또는 토큰 생성 중 오류가 발생했습니다.", e);
        }
    }
}
