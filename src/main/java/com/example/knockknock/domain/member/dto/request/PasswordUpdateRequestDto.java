package com.example.knockknock.domain.member.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequestDto {
    private String code;
    private String newPassword;
}
