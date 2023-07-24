package com.example.knockknock.domain.postlike.service;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.postlike.entity.PostLike;
import com.example.knockknock.domain.postlike.repository.PostLikeRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public void likePost(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        Optional<PostLike> postLikeOptional = postLikeRepository.findByMemberAndPost(member, post);

        if (postLikeOptional.isPresent() && postLikeOptional.get().isLiked()) {
            throw new GlobalException(GlobalErrorCode.DUPLICATE_LIKE);
        }

        if (postLikeOptional.isEmpty()) {
            postLikeRepository.save(PostLike.builder()
                    .member(member)
                    .post(post)
                    .isLiked(true)
                    .build());
        } else {
            postLikeOptional.get().likePost();
        }
        post.addLike();
    }

    @Transactional
    public void deletePostLike(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_LIKE_NOT_FOUND));

        if (!postLike.isLiked()) {
            throw new GlobalException(GlobalErrorCode.DUPLICATE_DELETE_LIKE);
        }

        post.removeLike();
        postLike.deletePostLike();
    }
}
