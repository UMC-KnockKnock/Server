package com.example.knockknock.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

    private String name;
    private String nickName;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "010-xxxx-xxxx 형식으로 입력해주세요.")
    private String phone;
    private String email;
    private Integer birthDay;

}
