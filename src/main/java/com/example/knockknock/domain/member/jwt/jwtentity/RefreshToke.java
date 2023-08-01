package com.example.knockknock.domain.member.jwt.jwtentity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity

public class RefreshToke {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false, unique = true, name = "member_id")
    private Long memberId;

    @Column(unique = false, nullable = false)
    private String refreshToken;

    public RefreshToke(Long memberId, String refreshToken){
        this.memberId = memberId;
        this.refreshToken = refreshToken;
    }

    public RefreshToke update(String newRefreshToken){
        this.refreshToken = newRefreshToken;
        return this;
    }




}
