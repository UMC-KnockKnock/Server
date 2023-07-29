package com.example.knockknock.domain.post.dto.request;
import com.example.knockknock.domain.board.entity.BoardType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequestDto {
    @NotNull
    private Long memberId;
    @NotNull
    private BoardType boardType;
    @NotNull
    @Builder.Default
    private Boolean isAnonymous = false;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
