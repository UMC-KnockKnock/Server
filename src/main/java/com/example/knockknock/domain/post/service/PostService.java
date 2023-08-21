package com.example.knockknock.domain.post.service;

import com.example.knockknock.domain.hashtag.dto.HashtagRegisterRequestDto;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.domain.post.dto.request.*;
import com.example.knockknock.domain.post.dto.response.*;
import com.example.knockknock.domain.hashtag.entity.Hashtag;
import com.example.knockknock.domain.hashtag.repository.HashtagRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.postimage.PostImage;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberIsLoginService memberIsLoginService;

    private final S3Service s3Service;

    @Transactional
    public void createPost(PostCreateRequestDto request, List<MultipartFile> images, UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);
        Post post = Post.builder()
                .member(member)
                .boardType(request.getBoardType())
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(0)
                .isAnonymous(request.getIsAnonymous())
                .build();
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
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
    public CountsResponseDto getPostCountings(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
        return CountsResponseDto.of(post);
    }

    @Transactional
    public List<MyPostsResponseDto> getMyPosts(UserDetailsImpl userDetails){
        Member member = memberIsLoginService.isLogin(userDetails);
        List<Post> myPosts = postRepository.findByMember(member);
        List<MyPostsResponseDto> myPostsResponseDtos = myPosts.stream()
                .map(MyPostsResponseDto::of)
                .collect(Collectors.toList());

        return myPostsResponseDtos;
    }

    @Transactional
    public Boolean isMyPost(Long postId, UserDetailsImpl userDetails){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        Long currentMemberId = userDetails.getUser().getMemberId();

        // 게시글 작성자 정보 가져오기
        Long postAuthorId = post.getMember().getMemberId();

        return currentMemberId.equals(postAuthorId);
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequestDto request, List<MultipartFile> images) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        post.updatePost(request);
        //이미지들 모두 지우기
        post.removeAllPostImages();

        if (images == null || images.isEmpty()) {
            return;
        }

        //새로 들어온 이미지로 변경 - 이미 있던 이미지와 동일하더라도 uuid값은 다르기 때문에 이것이 가능
        for (MultipartFile image : images) {
            String imageUrl = null;
            try {
                imageUrl = s3Service.uploadImage(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            post.addPostImage(imageUrl);
        }
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

    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
        return PostDetailResponseDto.of(post);
    }
}
