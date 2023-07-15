package com.example.knockknock.domain.post.dto.response;
import com.example.knockknock.domain.post.entity.Post;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsByAgeResponseDto {
    private Long postId;
    private String boardName;
    private String nickName;
    private String title;
    private String content;

    public static GetPostsByAgeResponseDto from(Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }
        return GetPostsByAgeResponseDto.builder()
                .postId(post.getId())
                .boardName(post.getBoard().getBoardName())
                .nickName(nickName)
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
