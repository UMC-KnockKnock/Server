package com.example.knockknock.dto;
import com.example.knockknock.domain.model.Post;
import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetPostListResponseDto {

    private Long postId;
    private Long boardId;
    private String nickName;
    private String title;


    public static GetPostListResponseDto from(Post post) {
        return GetPostListResponseDto.builder()
                .postId(post.getId())
                .boardId(post.getBoard().getId())
                .nickName(post.getUser().getNickName())
                .title(post.getTitle())
                .build();
    }


}
