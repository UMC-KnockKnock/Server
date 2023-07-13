package com.example.knockknock.global.dto.request;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HashtagRegisterRequestDto {
    @NotBlank
    private String tagName;
}
