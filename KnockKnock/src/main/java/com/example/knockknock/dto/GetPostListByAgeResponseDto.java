package com.example.knockknock.dto;
import com.example.knockknock.domain.model.Post;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetPostListByAgeResponseDto {
    private Long postId;
    private Long boardId;
    private String nickName;
    private String title;

    public static GetPostListByAgeResponseDto from(Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }
        return GetPostListByAgeResponseDto.builder()
                .postId(post.getId())
                .boardId(post.getBoard().getId())
                .nickName(nickName)
                .title(post.getTitle())
                .build();
    }
}
