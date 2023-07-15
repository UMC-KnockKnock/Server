package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.dto.GetMembersResponseDto;
import com.example.knockknock.domain.member.dto.MemberDetailResponseDto;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.dto.MemberSignUpRequestDto;
import com.example.knockknock.domain.member.dto.MemberUpdateRequestDto;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.global.exception.NotFoundPostException;
import com.example.knockknock.global.exception.NotFoundMemberException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    @Transactional
    public void createMember(MemberSignUpRequestDto request) {
        Member member = Member.builder()
                .memberName(request.getMemberName())
                .memberGender(request.getMemberGender())
                .nickName(request.getNickName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .birthDay(request.getBirthDay())
                .build();
        member.calculateAge();
        memberRepository.save(member);
    }

    @Transactional
    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundPostException("사용자를 찾을 수 없습니다."));
        return MemberDetailResponseDto.of(member);
    }

    @Transactional
    public List<GetMembersResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(GetMembersResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateMember(Long memberId, MemberUpdateRequestDto request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        member.updateMember(request);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

}
