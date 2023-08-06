package com.example.knockknock.domain.member.security.filter;

import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final String NO_CHECK_URL = "/login";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(request.getRequestURI().equals(NO_CHECK_URL)) {
            filterChain.doFilter(request, response);
            return;//안해주면 아래로 내려가서 계속 필터를 진행해버림
        }

        String refreshToken = jwtService.extractRefreshToken(request).filter(jwtService::isTokenValid).orElse(null);


        if(refreshToken != null){
            checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
            return;
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);

    }




    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request).filter(jwtService::isTokenValid).ifPresent(

                accessToken -> jwtService.extractEmail(accessToken).ifPresent(

                        username -> memberRepository.findByEmail(username).ifPresent(

                                this::saveAuthentication
                        )
                )
        );

        filterChain.doFilter(request,response);
    }



    private void saveAuthentication(Member member) {
        UserDetails user = User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getSocialType().name())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null,authoritiesMapper.mapAuthorities(user.getAuthorities()));


        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {


        memberRepository.findByRefreshToken(refreshToken).ifPresent(
                member -> jwtService.sendToken(response, jwtService.createAccessToken(member.getEmail()))
        );


    }
}
