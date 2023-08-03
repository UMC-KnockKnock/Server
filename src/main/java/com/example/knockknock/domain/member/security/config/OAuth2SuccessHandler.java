package com.example.knockknock.domain.member.security.config;

import com.example.knockknock.domain.member.CookieUtil;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.jwt.TokenProvider;
import com.example.knockknock.domain.member.jwt.jwtRepository.RefreshTokenRepository;
import com.example.knockknock.domain.member.jwt.jwtentity.RefreshToke;
import com.example.knockknock.domain.member.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.Association;
import org.springframework.data.mapping.PersistentProperty;
import org.springframework.data.mapping.SimpleAssociationHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.Duration;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static Duration ACCESS_TOKEN_DURATION = Duration.ofDays(1);

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository;
    private final MemberService memberService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2User auth2User = (OAuth2User) authentication.getPrincipal();
        Member byEmail = memberService.findByEmail((String) auth2User.getAttributes().get("email"));
        String refreshToken = tokenProvider.generateToken(byEmail, REFRESH_TOKEN_DURATION);
        saveRefreshToken(byEmail.getMemberId(), refreshToken);
        addRefreshTokenCookie(request, response, refreshToken);


        String accessToken = tokenProvider.generateToken(byEmail, ACCESS_TOKEN_DURATION);
        String targetUrl = getTargetUrl(accessToken);

        clearAuthenticationAttributes(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);

    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        oAuth2AuthorizationRequestBasedOnCookieRepository.removeAuthorizationRequest(request, response);
    }

    private String getTargetUrl(String accessToken) {
        return UriComponentsBuilder.fromUriString("/")
                .queryParam("token", accessToken)
                .build()
                .toString();
    }

    private void addRefreshTokenCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN_COOKIE_NAME);
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge);
    }

    private void saveRefreshToken(Long memberId, String generateToken) {
        RefreshToke refreshTokenRepositoryByMemberId = refreshTokenRepository.findByMemberId(memberId)
                .map(entity -> entity.update(generateToken))
                .orElse(new RefreshToke(memberId, generateToken));
        refreshTokenRepository.save(refreshTokenRepositoryByMemberId);
    }

}
