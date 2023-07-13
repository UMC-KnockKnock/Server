package com.example.knockknock.global.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequestDto {
    @NotBlank
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String nickName;
    private String birthDay;
}
