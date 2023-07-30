package com.example.knockknock.domain.member.security;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
// 로그인시 유저 정보를 가저오는 역할 클래스
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(id))
            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new UserDetailsImpl(member, id);
    }
}