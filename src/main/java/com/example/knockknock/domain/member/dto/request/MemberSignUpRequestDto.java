package com.example.knockknock.domain.member.dto.request;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequestDto {
    private String memberGender;
    @NotNull
    private String nickName;
    private String email;
    private String birthday;
    private String password;


}
