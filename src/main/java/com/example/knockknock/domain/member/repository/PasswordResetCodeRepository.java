package com.example.knockknock.domain.member.repository;

import com.example.knockknock.domain.member.entity.PasswordResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetCodeRepository extends JpaRepository<PasswordResetCode, Long> {
    PasswordResetCode findByCode(String code);
}
