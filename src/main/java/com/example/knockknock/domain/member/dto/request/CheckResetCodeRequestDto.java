package com.example.knockknock.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CheckResetCodeRequestDto {
    @NotBlank(message = "인증 코드를 입력해 주세요.")
    private String code;
}
