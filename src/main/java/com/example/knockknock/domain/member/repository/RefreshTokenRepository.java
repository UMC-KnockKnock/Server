package com.example.knockknock.domain.member.repository;

import com.example.knockknock.domain.member.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberEmail(String memberEmail);

    void deleteByRefreshToken(String refreshToken);
}
