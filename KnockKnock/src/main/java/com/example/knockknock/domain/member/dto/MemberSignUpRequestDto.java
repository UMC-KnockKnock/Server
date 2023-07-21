package com.example.knockknock.domain.member.dto;
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
    @NotNull
    private String memberName;
    private Gender memberGender;
    @NotNull
    private String nickName;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
    private String phoneNumber;
    private String email;
    private String birthday;

    public Gender getMemberGender() {
        return Gender.valueOf(String.valueOf(memberGender));
    }
}
