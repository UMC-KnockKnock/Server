package com.example.knockknock.domain.member.dto;

import com.example.knockknock.domain.member.entity.Member;
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
    private String birthDay;
    private Integer age;

    public static MemberDetailResponseDto of(Member member) {
        String formattedBirthDay = formatDate(member.getBirthDay());
        return MemberDetailResponseDto.builder()
                .memberId(member.getId())
                .name(member.getName())
                .nickName(member.getNickName())
                .birthDay(formattedBirthDay)
                .age(member.getAge())
                .build();

    }
    private static String formatDate(String birthDay) {
        String month = birthDay.substring(2, 4);
        String day = birthDay.substring(4, 6);
        return month + "월 " + day + "일";
    }
}
