package com.example.knockknock.domain.member.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    @Column(name = "member_email")
    private String memberEmail;
    private String refreshToken;

    public RefreshToken(String refreshToken, String memberEmail){
        this.refreshToken = refreshToken;
        this.memberEmail = memberEmail;
    }

}
