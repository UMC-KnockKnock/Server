package com.example.knockknock.domain.member.dto;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.entity.SocialType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class MemberSignupDto {

    @NotBlank(message = "아이디를 입력해주세요")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
    private String email;


    @NotBlank(message = "이름를 입력 해주세요")
    private String name;

    @NotBlank(message = "닉네임 입력 해주세요")
    private String nickname;

    /*@Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
    message = "비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내) 입니다") */
    private String password;

    @NotBlank(message = "생일 입력해주세요")
    private String birthday;







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