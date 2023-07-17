package com.example.knockknock.domain.post.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class HashtagRegisterRequestDto {
    @NotBlank
    @Pattern(regexp = "^#\\S+$", message = "태그명은 '#' 문자로 시작해야 하며 영숫자만 포함해야 합니다.")
    private String tagName;
}
