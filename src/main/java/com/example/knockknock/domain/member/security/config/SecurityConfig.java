package com.example.knockknock.domain.member.security.config;

import com.example.knockknock.domain.member.repository.MemberRepository;

import com.example.knockknock.domain.member.security.filter.LoginAuthenticaiotnProcessingfilter;
import com.example.knockknock.domain.member.security.filter.JwtAuthenticationProcessingFilter;
import com.example.knockknock.domain.member.security.handler.LoginFailureHandler;
import com.example.knockknock.domain.member.security.service.JwtService;
import com.example.knockknock.domain.member.security.handler.LoginSuccessHandler;
import com.example.knockknock.domain.member.security.service.MemberDetailService;
import com.example.knockknock.domain.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.security.web.authentication.logout.LogoutFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final MemberDetailService memberDetailService;
    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;





    @Bean
    public WebSecurityCustomizer customizer(){
        return (web -> web.ignoring()
                .requestMatchers("/h2-console/*")
                .requestMatchers("swagger-ui/**")

        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(login -> login.disable())
                .httpBasic(httpbasic -> httpbasic.disable())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 설정 안함

                .authorizeHttpRequests(request -> request
                        .requestMatchers("member/login", "member/signup", "member/**").permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2Login(auth2Login -> auth2Login
                        .loginPage("/login/auth")
                      //     .userInfoEndpoint(point -> point
                                //.userService(oAuth2UserCustomService)
                //))
                )
                .addFilterAfter(loginAuthenticaiotnProcessingfilter(), LogoutFilter.class)
                .addFilterBefore(jwtAuthenticationProcessingFilter(), LoginAuthenticaiotnProcessingfilter.class)
                .logout(logout -> logout
                        .logoutSuccessUrl("/logout"));

        return http.build();
    }





    /*
    DaoAuthentication 비밀번호 확인
     */

    @Bean
    public AuthenticationManager authenticationManger() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(memberDetailService);
    return new ProviderManager(daoAuthenticationProvider);
    }

    /*
    로그인 실패시
     */
    @Bean
    public LoginFailureHandler loginFailureHandler(){
        return new LoginFailureHandler();
    }

    /*
     로그인 이후 작업업     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler(jwtService, memberRepository);
    }

    @Bean
    public LoginAuthenticaiotnProcessingfilter loginAuthenticaiotnProcessingfilter(){
        LoginAuthenticaiotnProcessingfilter loginAuthenticaiotnProcessingfilter =new LoginAuthenticaiotnProcessingfilter(objectMapper);
        loginAuthenticaiotnProcessingfilter.setAuthenticationManager(authenticationManger());
        loginAuthenticaiotnProcessingfilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        loginAuthenticaiotnProcessingfilter.setAuthenticationFailureHandler(loginFailureHandler());;
        return loginAuthenticaiotnProcessingfilter;
    }


    @Bean
    public JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter(){
        JwtAuthenticationProcessingFilter jwtAuthenticationProcessingFilter = new JwtAuthenticationProcessingFilter(jwtService, memberRepository);
        return jwtAuthenticationProcessingFilter;
    }






 }






