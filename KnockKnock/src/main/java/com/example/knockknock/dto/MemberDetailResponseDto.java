package com.example.knockknock.dto;

import com.example.knockknock.domain.model.Member;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailResponseDto {
    private Long memberId;
    private String name;
    private String nickName;

    public static MemberDetailResponseDto of(Member member) {
        return MemberDetailResponseDto.builder()
                .memberId(member.getId())
                .nickName(member.getNickName())
                .build();
    }
}
