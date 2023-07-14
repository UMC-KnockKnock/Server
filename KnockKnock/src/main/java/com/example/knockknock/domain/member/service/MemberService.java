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
    public List<GetMembersResponseDto> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(GetMembersResponseDto::from)
                .collect(Collectors.toList());
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
