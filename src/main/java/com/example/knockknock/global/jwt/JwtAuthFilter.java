package com.example.knockknock.global.jwt;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.global.message.ResponseMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
// 들어오는 요청 처리
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;


    /* 요청이 들어올 때마다 실행.
     * 토큰 확인, 토큰 유효성 검사, 토큰에 포함된 정보를 기반으로 인증 수행 */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // HTTP 요청에서 Authorization헤더를 찾아 토큰 반환
        String accessToken = jwtUtil.resolveToken(request, "Access");


        // 토큰이 있다면 진행
        if(accessToken != null) {
            if(!jwtUtil.validateToken(accessToken)){
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            // 토큰 유효 -> getUserInfoFromToken메서드를 사용해 JWT 토큰의 payload에서 정보 반환
            Claims info = jwtUtil.getUserInfoFromToken(accessToken);    // 토큰에서 user정보 가져옴(payload)
            // Claims 객체에서 사용자 이름을 가져와 인증 설정
            setAuthentication(info.getSubject());   // getSubject 헤더값

            Date expiration = info.getExpiration();
            setAuthentication(info.getSubject());

        }
        // 다음 단계 실행 -> 다른 필터 및 컨트롤러 실행
        filterChain.doFilter(request,response);
    }

    /* 주어진 파라미터 값으로 SecurityContext에 인증 설정.
     * JWT 유틸리티를 사용하여 인증 객체를 생성하고, SecurityContext에 설정*/
    public void setAuthentication(String memberEmail) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(memberEmail);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    private String getRefreshTokenFromRequest(HttpServletRequest request) {
        String refreshToken = request.getHeader("Refresh-Token");
        if (StringUtils.hasText(refreshToken)) {
            return refreshToken;
        }
        return null;
    }


    // JWT 인증과 관련된 예외 처리
    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");

        try {
            String json = new ObjectMapper().writeValueAsString(ResponseMessage.ErrorResponse(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
