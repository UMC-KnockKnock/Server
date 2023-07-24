package com.example.knockknock.domain.postlike.service;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.postlike.dto.PostLikeRequestDto;
import com.example.knockknock.domain.postlike.entity.PostLike;
import com.example.knockknock.domain.postlike.repository.PostLikeRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public String likePost(PostLikeRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post)
                .orElse(PostLike.builder()
                        .member(member)
                        .post(post)
                        .isLiked(false)
                        .build());
        postLikeRepository.save(postLike);
        if (postLike.isLiked()) {
            postLike.deletePostLike();
            post.removeLike();
            return "좋아요가 취소되었습니다.";
        } else {
            postLike.likePost();
            post.addLike();
            return "좋아요를 눌렀습니다.";
        }
    }
}
