package com.example.knockknock.domain.post.dto.response;
import com.example.knockknock.domain.board.entity.BoardType;
import com.example.knockknock.domain.hashtag.entity.Hashtag;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.postimage.entity.PostImage;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private int boardType;
    private String nickName;
    private String title;
    private String content;
    private List<String> imageUrl;
    private int likeCount;
    private int commentCount;
    private int reportCount;

    private List<String> hashtags;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedAt;

    public static PostDetailResponseDto of(Post post) {
        String nickName;
        if (post.getIsAnonymous()) {
            nickName = "익명";
        } else {
            nickName = post.getMember().getNickName();
        }

        List<String> imageUrls = post.getPostImages().stream()
                .map(PostImage::getPostImageUrl)
                .collect(Collectors.toList());

        List<String> hashtagNames = post.getHashtags().stream()
                .map(Hashtag::getTagName)
                .collect(Collectors.toList());

        return PostDetailResponseDto.builder()
                .postId(post.getPostId())
                .boardType(post.getBoardType().ordinal())
                .nickName(nickName)
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(imageUrls)
                .likeCount(post.getLikeCount())
                .commentCount(post.getComments().size())
                .reportCount(post.getReports().size())
                .hashtags(hashtagNames)
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();
    }
}
