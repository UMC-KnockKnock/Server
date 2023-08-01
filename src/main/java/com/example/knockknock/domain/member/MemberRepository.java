package com.example.knockknock.domain.member;

import com.example.knockknock.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickName(String nickName);

    Optional<Boolean> existsByEmail(String email);
}
