package com.example.knockknock.domain.post.dto.response;

import com.example.knockknock.domain.post.entity.Hashtag;
import com.example.knockknock.domain.post.entity.Post;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsByHashtagResponseDto {
    private Long postId;
    private String nickName;
    private String title;
    private String content;
    private List<String> hashtags;

    public static GetPostsByHashtagResponseDto from(Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }
        List<String> hashtagNames = post.getHashtags().stream()
                .map(Hashtag::getTagName)
                .collect(Collectors.toList());

        return GetPostsByHashtagResponseDto.builder()
                .postId(post.getId())
                .nickName(nickName)
                .title(post.getTitle())
                .content(post.getContent())
                .hashtags(hashtagNames)
                .build();
    }
}
