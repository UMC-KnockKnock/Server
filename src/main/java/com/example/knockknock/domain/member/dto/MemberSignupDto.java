package com.example.knockknock.domain.member.dto;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.entity.SocialType;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class MemberSignupDto {


    private String email;

    private String name;


    private String nickname;

    private String password;

    private String birthday;

    public MemberSignupDto(String email, String password, String nickname, String name, String birthday) {
    }






    public Member signUpToEntity(MemberSignupDto memberSignupDto) {
        return Member.builder()
                .email(memberSignupDto.getEmail())
                .nickName(memberSignupDto.getNickname())
                .memberName(memberSignupDto.getName())
                .password(memberSignupDto.getPassword())
                .birthday(memberSignupDto.getBirthday())
                .socialType(SocialType.User)
                .build();

    }
}