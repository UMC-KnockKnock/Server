package com.example.knockknock.domain.member.service;

import com.example.knockknock.domain.member.dto.MemberSignupDto;
import com.example.knockknock.domain.member.dto.MemberUpdateDto;
import com.example.knockknock.domain.member.entity.Member;

public interface MemberService {

    void signup(MemberSignupDto memberSignupDto) throws Exception;

    void update(MemberUpdateDto memberUpdateDto) throws  Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;


    Member findByMemberId(Long memberId);
    Member findByEmail(String email);

    Member getInfo(Long id) throws Exception;
    Member getMyInfo() throws Exception;

}
