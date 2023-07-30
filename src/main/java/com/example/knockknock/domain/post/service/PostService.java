package com.example.knockknock.domain.post.service;

import com.example.knockknock.domain.hashtag.dto.HashtagRegisterRequestDto;
import com.example.knockknock.domain.post.dto.request.*;
import com.example.knockknock.domain.post.dto.response.*;
import com.example.knockknock.domain.hashtag.entity.Hashtag;
import com.example.knockknock.domain.hashtag.repository.HashtagRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.postlike.repository.PostLikeRepository;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.postlike.entity.PostLike;
import com.example.knockknock.global.exception.*;
import com.example.knockknock.global.image.service.S3Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final S3Service s3Service;

    @Transactional
    public void createPost(PostCreateRequestDto request, List<MultipartFile> images) {
        Member member =  memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));
        Post post = Post.builder()
                .member(member)
                .boardType(request.getBoardType())
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(0)
                .isAnonymous(request.getIsAnonymous())
                .build();
        for (MultipartFile image : images) {
            if (image != null && !image.isEmpty()) {
                String imageUrl = null;
                try {
                    imageUrl = s3Service.uploadImage(image);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                post.addPostImage(imageUrl);
            }
        }

        postRepository.save(post);
    }

    @Transactional
    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
        return PostDetailResponseDto.of(post);
    }

    @Transactional
    public void updatePost(Long postId , PostUpdateRequestDto request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
        post.updatePost(request);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
        postRepository.delete(post);
    }

    @Transactional
    public String sharePost(Long postId, HttpServletRequest request){
        String domain = request.getServerName();
        int port = request.getServerPort();
        String shareUrl = String.format("http://%s:%d/post/%d", domain, port, postId);
        return shareUrl;
    }

}
