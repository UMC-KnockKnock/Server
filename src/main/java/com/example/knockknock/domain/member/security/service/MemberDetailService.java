package com.example.knockknock.domain.member.security.service;

import com.example.knockknock.domain.member.MemberRepository;
import com.example.knockknock.domain.member.entity.Member;

import com.example.knockknock.domain.member.security.securityEntity.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public MemberDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member principal = memberRepository.findByEmail(username)
                .orElseThrow(() -> {
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다" + username);
                });
        return new MemberDetails(principal);

    }
}