package com.example.knockknock.global.jwt;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.RefreshTokenRepository;
import com.example.knockknock.domain.member.security.RefreshToken;
import com.example.knockknock.domain.member.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Refresh-Token";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000L;

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    // signature 생성 알고리즘 /토큰을 인코딩하거나 유효성 검증을 할 때 사용하는 고유한 암호화 코드
    // signature -> jwt가 변경되지 않았음을 검증하는 역할
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // key 객체 생성
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request, String tokenType) {
        String headerName;

        if ("Access".equals(tokenType)) {
            headerName = ACCESS_TOKEN_HEADER; // 올바른 상수 이름이어야 합니다.
        } else if ("Refresh".equals(tokenType)) {
            headerName = REFRESH_TOKEN_HEADER; // 올바른 상수 이름이어야 합니다.
        } else {
            return null; // 또는 적절한 예외를 던질 수 있습니다.
        }

        String token = request.getHeader(headerName);
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        return null;
    }

    // 토큰 생성
    public String createAccessToken(String memberEmail) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(memberEmail)
//                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }
    public String createRefreshToken(String memberEmail) {
        Date date = new Date();
        return  BEARER_PREFIX +
                Jwts.builder()
                .setSubject(memberEmail)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                .setIssuedAt(date)
                .signWith(key, signatureAlgorithm)
                .compact();
    }


    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public boolean validateRefreshToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            log.warn("Refresh token validation failed in the first phase");
            return false;
        }

        // 저장된 토큰과 비교
        String memberEmail = getMemberEmailFromToken(refreshToken);
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByMemberEmail(memberEmail);

        if (!refreshTokenOptional.isPresent()) {
            log.warn("No matching refresh token found for email: {}", memberEmail);
            return false;
        }

        boolean isEqual = refreshToken.equals(refreshTokenOptional.get().getRefreshToken());
        if (!isEqual) {
            log.warn("Refresh token mismatch for email: {}", memberEmail);
        }

        return isEqual;

    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        // jwt 토큰을 파싱해서 그 안에 들어있는 클레임을 추출하는 코드
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getMemberEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 인증 객체 생성
    // SecurityContextHolder에 담을 Authentication을 가져옴
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        // UsernamePasswordAuthenticationToken -> 인증된 사용자의 인증 정보를 나타내는 객체로, 인증된 사용자의 정보와 권한을 포함
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}