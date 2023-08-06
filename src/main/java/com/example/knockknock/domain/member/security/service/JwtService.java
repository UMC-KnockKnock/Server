package com.example.knockknock.domain.member.security.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

public interface JwtService {

    String createAccessToken(String email);

    String createRefreshToken();

    void updateRefreshToken(String email, String refreshToken);
    void destroyRefreshToken(String email);

    void sendToken(HttpServletResponse response, String accessToken);
    void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) throws IOException,ServletException;


    Optional<String> extractAccessToken(HttpServletRequest request) throws IOException, ServletException;
    Optional<String> extractRefreshToken(HttpServletRequest request) throws IOException, ServletException;

    Optional<String> extractEmail(String accessToken);

    void setAccessTokenHeader(HttpServletResponse response, String accessToken);

    void setRefreshToken(HttpServletResponse response, String refreshToken);

    boolean isTokenValid(String token);
}
