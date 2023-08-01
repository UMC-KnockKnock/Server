package com.example.knockknock.domain.member.jwt;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties("jwt")
public class JwtProperties {

    private String issuer;

    private String secretKey;
}
