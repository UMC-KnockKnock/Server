package com.example.knockknock.service;

import com.example.knockknock.domain.model.Member;
import com.example.knockknock.domain.repository.MemberRepository;
import com.example.knockknock.dto.MemberDetailResponseDto;
import com.example.knockknock.dto.MemberSignUpRequestDto;
import com.example.knockknock.dto.MemberUpdateRequestDto;
import com.example.knockknock.exception.NotFoundPostException;
import com.example.knockknock.exception.NotFoundMemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public void createUser(MemberSignUpRequestDto request) {
        memberRepository.save(Member.builder()
                .id(request.getId())
                .name(request.getName())
                .nickName(request.getNickName())
                .build());
    }

    @Transactional
    public MemberDetailResponseDto getUserDetail(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("사용자를 찾을 수 없습니다."));
        log.info("User: {}", member);
        return MemberDetailResponseDto.of(member);
    }

    @Transactional
    public void updateUser(Long id, MemberUpdateRequestDto request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        member.updateUser(request);
    }

    @Transactional
    public void deleteUser(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

}
