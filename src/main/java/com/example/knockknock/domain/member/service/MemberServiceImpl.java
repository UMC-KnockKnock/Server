package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.MemberRepository;
import com.example.knockknock.domain.member.SecurityUtil;
import com.example.knockknock.domain.member.dto.MemberSignupDto;
import com.example.knockknock.domain.member.dto.MemberUpdateDto;
import com.example.knockknock.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Transactional
    @Override
    public void signup(MemberSignupDto memberSignupDto) throws Exception {
        Member signUpToEntity = memberSignupDto.signUpToEntity(memberSignupDto);


        if (!memberRepository.existsByEmail(memberSignupDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("이미 있는 아이디 입니다" +  memberSignupDto.getEmail());
        }
        memberRepository.findByNickName(memberSignupDto.getNickname()).orElseThrow
                (()-> new IllegalArgumentException("이미 사용 중인 별칭 입니다" + memberSignupDto.getNickname()));


        memberRepository.save(signUpToEntity);

    }


    @Transactional
    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.LoginUsername())
                .orElseThrow(()-> new IllegalArgumentException("회원 정보가 없습니다" + memberUpdateDto.email()));
        memberUpdateDto.phoneNumber().ifPresent(member::upMyPhoneNumber);
        memberUpdateDto.email().ifPresent(member::upUsername);
        memberUpdateDto.nickName().ifPresent(member::updateNickName);



    }



    @Transactional
    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.LoginUsername())
                .orElseThrow(()-> new IllegalArgumentException("회원 정보가 없습니다"));

        if(!member.matchPassword(bCryptPasswordEncoder(), checkPassword)){
            throw new IllegalArgumentException("비밀번호가 틀립니다" + checkPassword);
        }

        member.upPassword(bCryptPasswordEncoder(), toBePassword);


    }

    @Transactional
    @Override
    public void withdraw(String checkPassword) throws Exception {

        Member member = memberRepository.findByEmail(SecurityUtil.LoginUsername())
                .orElseThrow(()-> new IllegalArgumentException("회원 정보가 없습니다"));

        if(!member.matchPassword(bCryptPasswordEncoder(), checkPassword)){
            throw new IllegalArgumentException("비밀번호가 틀립니다" + checkPassword);
        }

        memberRepository.delete(member);


    }

    @Override
    public Member getInfo(Long id) throws Exception {
        return memberRepository.findById(id).orElseThrow(()-> new
                IllegalArgumentException("회원 조회 실패 했습니다"+ id));
    }

    @Override
    public Member getMyInfo() throws Exception {
        return memberRepository.findByEmail(SecurityUtil.LoginUsername()).orElseThrow(()-> new IllegalArgumentException("회원을 찾을 수 없습니다") );
    }


    @Override
    public Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() ->new IllegalArgumentException("없는 계정 입니다"));
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("아이디가 없습니다"));
    }
}
