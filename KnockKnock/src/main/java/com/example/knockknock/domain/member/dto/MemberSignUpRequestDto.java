package com.example.knockknock.domain.member.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequestDto {
    @NotNull
    private String name;
    @NotNull
    private String nickName;
    private String birthDay;
}
