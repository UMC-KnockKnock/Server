package com.example.knockknock.domain.service;

import com.example.knockknock.domain.model.Member;
import com.example.knockknock.domain.repository.MemberRepository;
import com.example.knockknock.global.dto.response.MemberDetailResponseDto;
import com.example.knockknock.global.dto.request.MemberSignUpRequestDto;
import com.example.knockknock.global.dto.request.MemberUpdateRequestDto;
import com.example.knockknock.global.exception.NotFoundPostException;
import com.example.knockknock.global.exception.NotFoundMemberException;
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
    public void createMember(MemberSignUpRequestDto request) {
        Member member = Member.builder()
                .name(request.getName())
                .nickName(request.getNickName())
                .birthDay(request.getBirthDay())
                .build();
        member.calculateAge();
        memberRepository.save(member);
    }

    @Transactional
    public MemberDetailResponseDto getMemberDetail(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("사용자를 찾을 수 없습니다."));
        return MemberDetailResponseDto.of(member);
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequestDto request) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        member.updateMember(request);
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

}
