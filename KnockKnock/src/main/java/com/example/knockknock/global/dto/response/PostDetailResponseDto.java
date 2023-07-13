package com.example.knockknock.global.dto.response;
import lombok.*;
import com.example.knockknock.domain.model.Post;
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResponseDto {
    private Long postId;

    private String nickName;

    private String title;

    private String content;

    private int commentCount;

    public static PostDetailResponseDto of(Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }

        return PostDetailResponseDto.builder()
                .postId(post.getId())
                .nickName(nickName)
                .title(post.getTitle())
                .content(post.getContent())
                .commentCount(post.getComments().size())
                .build();
    }
}
