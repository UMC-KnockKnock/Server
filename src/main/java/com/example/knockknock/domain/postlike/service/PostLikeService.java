package com.example.knockknock.domain.postlike.service;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.postlike.dto.PostLikeDetailResponseDto;
import com.example.knockknock.domain.postlike.entity.PostLike;
import com.example.knockknock.domain.postlike.repository.PostLikeRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;

    private final PostRepository postRepository;

    private final MemberRepository memberRepository;

    private final MemberIsLoginService memberIsLoginService;

    @Transactional
    public String likePost(Long postId, UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        Optional<PostLike> postLikeOptional = postLikeRepository.findByMemberAndPost(member, post);
        if(postLikeOptional.isPresent()){
            PostLike postLike = postLikeOptional.get();
            post.removeLike();
            postLikeRepository.delete(postLike);
            return "좋아요를 취소했습니다.";
        } else {
            PostLike postLike = PostLike.builder()
                    .member(member)
                    .post(post)
                    .build();
            post.addLike();
            postLikeRepository.save(postLike);
            return "좋아요를 눌렀습니다.";
        }
    }

    @Transactional
    public List<PostLikeDetailResponseDto> getPostLikes(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        List<PostLike> postLikes = postLikeRepository.findByPost(post);
        return postLikes.stream()
                .map(PostLikeDetailResponseDto::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
