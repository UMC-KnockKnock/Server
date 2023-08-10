package com.example.knockknock.domain.member.dto.request;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateRequestDto {

    private String nickName;
    private String birthday;


}
