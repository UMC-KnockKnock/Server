package com.example.knockknock.domain.member.dto;

import com.example.knockknock.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;


@Getter
public class MemberInfoDto {

    private  String name;

    private  String email;

    private  String nickname;

    @Builder
    public void UserInfoDto(Member member){
        this.name = member.getMemberName();
        this.email = member.getEmail();
        this.nickname = member.getNickName();
    }
}
