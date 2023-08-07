package com.example.knockknock.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmailAuthenticationRequestDto {
    @NotBlank(message = "이메일을 입력해 주세요.")
    private String email;
}
