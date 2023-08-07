package com.example.knockknock.domain.member.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService{

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String USERNAME_CLAIM = "email";
    private static final String BEARER = "Bearer ";


    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Value("${jwt.secret}")
    private String secret;
    //    secret: knockknock

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    //  expiration: 20000

    @Value("${jwt.access.header}")
    private String accessHeader;

    //header: Authorization

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    // expiration: 90

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    //     header : Authentication-refresh



    @Override
    public String createAccessToken(String email) {
        return JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis()+ accessExpiration* 1000))
                .withClaim(USERNAME_CLAIM,  email)
                .sign(Algorithm.HMAC512(secret));

    }

    @Override
    public String createRefreshToken() {
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration *1000))
                .sign(Algorithm.HMAC512(secret));
    }

    @Override
    public void updateRefreshToken(String email, String refreshToken) {
        memberRepository.findByEmail(email)
                .ifPresentOrElse(member -> member.updateRefreshToken(refreshToken),
                        () -> new IllegalAccessException("회원이 없습니다"));

    }

    @Override
    public void destroyRefreshToken(String email) {
        memberRepository.findByEmail(email)
                .ifPresentOrElse(member -> member.destroyRefreshToken(),
                        ()-> new IllegalAccessException("없는 회원 입니다"));
    }

    @Override
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshToken(response, refreshToken);

        Map<String, String> toKenMap = new HashMap<>();
        toKenMap.put(ACCESS_TOKEN_SUBJECT, accessToken);
        toKenMap.put(REFRESH_TOKEN_SUBJECT, refreshToken);

        String token = objectMapper.writeValueAsString(toKenMap);

        response.getWriter().write(token);
    }


    @Override
    public void sendToken(HttpServletResponse response, String accessToken)  {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);

        Map<String, String> toKenMap = new HashMap<>();
        toKenMap.put(ACCESS_TOKEN_SUBJECT,accessToken);
    }



    @Override
    public Optional<String> extractAccessToken(HttpServletRequest request) throws IOException, ServletException {
        return Optional.ofNullable(request.getHeader(accessHeader)).filter(
                accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

    @Override
    public Optional<String> extractRefreshToken(HttpServletRequest request) throws IOException, ServletException {

        return Optional.ofNullable(request.getHeader(refreshHeader)).filter(
                refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    @Override
    public Optional<String> extractEmail(String accessToken) {
        try {
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secret)).build().verify(accessToken).getClaim(USERNAME_CLAIM).asString());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
    response.setHeader(accessHeader,  accessToken);
    }


    @Override
    public void setRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader,  refreshToken);
    }

    @Override
    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secret)).build().verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    }

