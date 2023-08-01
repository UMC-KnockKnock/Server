package com.example.knockknock.domain.member.jwt.jwtRepository;

import com.example.knockknock.domain.member.jwt.jwtentity.RefreshToke;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToke, Long> {

    @Override
    Optional<RefreshToke> findById(Long memberId);

    Optional<RefreshToke> findByRefreshToken(String refreshToken);
}
