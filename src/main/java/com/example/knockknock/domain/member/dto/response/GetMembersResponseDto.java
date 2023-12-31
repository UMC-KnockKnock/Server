package com.example.knockknock.domain.member.dto.response;
import com.example.knockknock.domain.member.entity.Member;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetMembersResponseDto {
    private Long memberId;
    private String nickName;

    public static GetMembersResponseDto from(Member member) {
        return GetMembersResponseDto.builder()
                .memberId(member.getMemberId())
                .nickName(member.getNickName())
                .build();
    }
}
