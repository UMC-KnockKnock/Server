package com.example.knockknock.domain.post.service;

import com.example.knockknock.domain.post.dto.request.*;
import com.example.knockknock.domain.post.dto.response.*;
import com.example.knockknock.domain.post.entity.Hashtag;
import com.example.knockknock.domain.post.repository.HashtagRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.repository.PostLikeRepository;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.entity.PostLike;
import com.example.knockknock.global.exception.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;
    private PostLikeRepository postLikeRepository;
    private HashtagRepository hashtagRepository;
    private MemberRepository memberRepository;

    public PostService(PostRepository postRepository, PostLikeRepository postLikeRepository, HashtagRepository hashtagRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.postLikeRepository = postLikeRepository;
        this.hashtagRepository = hashtagRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createPost(PostCreateRequestDto request) {
        Member member =  memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));


        Post post = Post.builder()
                .member(member)
                .boardType(request.getBoardType())
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(0)
                .isAnonymous(request.getIsAnonymous())
                .build();

        postRepository.save(post);

    }

    @Transactional
    public PostDetailResponseDto getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        return PostDetailResponseDto.of(post);
    }

    @Transactional
    public void updatePost(Long id , PostUpdateRequestDto request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        post.updatePost(request);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    @Transactional
    public void likePost(Long memberId, Long postId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        Optional<PostLike> postLikeOptional = postLikeRepository.findByMemberAndPost(member, post);

        if (postLikeOptional.isPresent() && postLikeOptional.get().isLiked()) {
            throw new AlreadyLikeException("이미 좋아요한 게시물입니다.");
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
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        PostLike postLike = postLikeRepository.findByMemberAndPost(member, post)
                .orElseThrow(() -> new NotFoundLikeException("좋아요 정보를 찾을 수 없습니다."));

        if (!postLike.isLiked()) {
            throw new AlreadyDeleteLikeException("이미 좋아요가 취소된 게시글입니다.");
        }

        post.removeLike();
        postLike.deletePostLike();
    }

    @Transactional
    public void addHashtag(Long postId, HashtagRegisterRequestDto request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        hashtagRepository.save(Hashtag.builder()
                .post(post)
                .tagName(request.getTagName())
                .build());

    }

    @Transactional
    public void deleteHashtag(Long hashtagId) {

        Hashtag hashtag = hashtagRepository.findById(hashtagId)
                .orElseThrow(() -> new NotFoundHashtagException("해시태그를 찾을 수 없습니다."));

        hashtagRepository.delete(hashtag);
    }

    @Transactional
    public String sharePost(Long postId, HttpServletRequest request){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        String domain = request.getServerName();
        int port = request.getServerPort();
        String shareUrl = String.format("https://%s:%d/post/%d", domain, port, postId);
        return shareUrl;
    }
}
