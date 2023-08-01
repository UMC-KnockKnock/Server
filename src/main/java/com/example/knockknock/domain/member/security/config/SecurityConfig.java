package com.example.knockknock.domain.member.security.config;

import com.example.knockknock.domain.member.security.securityEntity.MemberDetailsProvider;
import com.example.knockknock.domain.member.security.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;


@EnableMethodSecurity
@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberDetailService memberDetailService;

    private final MemberDetailsProvider memberDetailsProvider;

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
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login", "/signup").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true));
        return http.build();
    }


    @Bean
    public DefaultSecurityFilterChain authenticationManage(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, MemberDetailService memberDetailService) throws Exception {
        http
                .getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(memberDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
        return http.build();
    }

   @Bean
   public BCryptPasswordEncoder bCryptPasswordEncoder(){
       return new BCryptPasswordEncoder();
   }

 }






