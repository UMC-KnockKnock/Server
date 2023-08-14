package com.example.knockknock.domain.member.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NicknameCheckRequestDto {
    private String nickName;
}
