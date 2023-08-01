package com.example.knockknock.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdatePasswordDto {

    String checkPassword;

    @NotBlank(message = "비밀번호를 입력해주세요")
    //@Pattern(regexp = "")
     String toBePassword;


}
