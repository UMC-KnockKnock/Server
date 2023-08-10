package com.example.knockknock.domain.member.repository;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.entity.PasswordResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {
    Optional<PasswordResetCode> findByCode(String code);

    Optional<PasswordResetCode> findByMember(Member member);
}
