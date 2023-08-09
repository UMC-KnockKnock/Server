package com.example.knockknock.domain.member.dto.request;
import com.example.knockknock.domain.member.entity.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignUpRequestDto {
    private Gender memberGender;
    @NotNull
    private String nickName;
    private String email;
    private String birthday;
    private String password;

    public Gender getMemberGender() {
        return Gender.valueOf(String.valueOf(memberGender));
    }
}
