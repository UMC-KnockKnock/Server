package com.example.knockknock.domain.board.dto.response;
import com.example.knockknock.domain.board.entity.Post;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsByAgeResponseDto {
    private Long postId;
    private Long boardId;
    private String nickName;
    private String title;

    public static GetPostsByAgeResponseDto from(Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }
        return GetPostsByAgeResponseDto.builder()
                .postId(post.getId())
                .boardId(post.getBoard().getBoardId())
                .nickName(nickName)
                .title(post.getTitle())
                .build();
    }
}
