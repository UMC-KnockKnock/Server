package com.example.knockknock.domain.member.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Slf4j
public class LoginAuthenticaiotnProcessingfilter extends AbstractAuthenticationProcessingFilter {

    private static final String DEFAULT_LOGIN_REQUEST_URL = "/login";

    private static final String HTTP_METHOD = "POST";

    private static final String CONTENT_TYPE = "application/json";

    private final ObjectMapper objectMapper;

    private static final String USERNAME_KEY = "email";

    private static final String PASSWORD_KET = "password";

    private static final AntPathRequestMatcher DEFAULT_LOGIN_MATCHER = new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL,HTTP_METHOD) ;


    public LoginAuthenticaiotnProcessingfilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_MATCHER);
        this.objectMapper = objectMapper;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (request.getContextPath() == null || !request.getContentType().equals(CONTENT_TYPE)) {
            throw new AuthenticationServiceException("인증 요청 타입이 맞지 않습니다");
        }

        log.info("Login page 시작 점");
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Map<String, String>  usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String username = usernamePasswordMap.get(USERNAME_KEY);
        String password = usernamePasswordMap.get(PASSWORD_KET);

        log.info("로그인 username : " + username);
        log.info("로그인 password : " + password);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        log.info("Manger 넣기 : " + authenticationToken);
        return this.getAuthenticationManager().authenticate(authenticationToken);
    }
}
