package com.example.knockknock.domain.member.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

    private String memberName;
    private String nickName;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
    private String phoneNumber;
    private String email;
    private String birthDay;


}
