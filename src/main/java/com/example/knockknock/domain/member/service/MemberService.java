package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.dto.*;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import com.example.knockknock.global.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public void signup(final MemberSignUpRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 중복 아이디 불가
        Optional<Member> found = memberRepository.findByEmail(email);
        if(found.isPresent()){
            throw new GlobalException(GlobalErrorCode.DUPLICATE_EMAIL);
        }

        createMember(signupRequestDto, password);
    }


    @Transactional
    public void createMember(MemberSignUpRequestDto request, String password) {
        Member member = Member.builder()
                .memberName(request.getMemberName())
                .memberGender(request.getMemberGender())
                .nickName(request.getNickName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .birthday(request.getBirthday())
                .password(password)
                .build();
        member.calculateAge();
        memberRepository.save(member);
    }

    // 유저 로그인
    @Transactional
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        Member member = memberRepository.findByEmail(email).orElseThrow(
                ()-> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND)
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, member.getPassword())){
            throw new GlobalException(GlobalErrorCode.PASSWORD_MISMATCH);
        }

        // http 응답에 헤더 추가
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(member));
    }


    @Transactional
    public MemberDetailResponseDto getMemberDetail(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

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
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        member.updateMember(request);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        memberRepository.delete(member);
    }

    public void test() {
        log.info("123123123");
    }
}
