package com.example.knockknock.domain.post.service;


import com.example.knockknock.domain.board.entity.*;
import com.example.knockknock.domain.board.repository.*;
import com.example.knockknock.domain.post.dto.request.GetPostsByAgeRequestDto;
import com.example.knockknock.domain.post.dto.request.PostCreateRequestDto;
import com.example.knockknock.domain.post.dto.request.PostUpdateRequestDto;
import com.example.knockknock.domain.post.dto.response.GetPostsByAgeResponseDto;
import com.example.knockknock.domain.post.dto.response.GetPostsResponseDto;
import com.example.knockknock.domain.post.dto.response.PostDetailResponseDto;
import com.example.knockknock.domain.post.entity.Hashtag;
import com.example.knockknock.domain.post.dto.request.HashtagRegisterRequestDto;
import com.example.knockknock.domain.post.dto.response.HashtagRegisterResponseDto;
import com.example.knockknock.domain.post.repository.HashtagRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.repository.PostLikeRepository;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.entity.PostLike;
import com.example.knockknock.global.exception.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private PostRepository postRepository;
    private BoardRepository boardRepository;
    private PostLikeRepository postLikeRepository;
    private HashtagRepository hashtagRepository;
    private MemberRepository memberRepository;

    public PostService(PostRepository postRepository, BoardRepository boardRepository, PostLikeRepository postLikeRepository, HashtagRepository hashtagRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
        this.postLikeRepository = postLikeRepository;
        this.hashtagRepository = hashtagRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void createPost(PostCreateRequestDto request) {
        Member member =  memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));

        Post post = Post.builder()
                .member(member)
                .board(board)
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(0)
                .isAnonymous(request.getIsAnonymous())
                .build();

        postRepository.save(post);

    }

    @Transactional
    public PostDetailResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        return PostDetailResponseDto.of(post);
    }

    @Transactional
    public List<GetPostsResponseDto> getPostsByBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));
        List<Post> posts = postRepository.findByBoard(board);
        return posts.stream()
                .map(GetPostsResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GetPostsByAgeResponseDto> getPostsByAgeGroup(GetPostsByAgeRequestDto request) {
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));
        int ageGroupStart = request.getAgeGroup();
        int ageGroupEnd = ageGroupStart + 9;
        List<Post> posts = postRepository.findByBoardAndMemberAgeBetween(board, ageGroupStart, ageGroupEnd);
        return posts.stream()
                .map(GetPostsByAgeResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(Long id , PostUpdateRequestDto request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        post.updatePost(request);
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
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
    public HashtagRegisterResponseDto addHashtag(Long id, HashtagRegisterRequestDto request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        hashtagRepository.save(Hashtag.builder()
                .post(post)
                .tagName(request.getTagName())
                .build());

        return new HashtagRegisterResponseDto(request.getTagName());
    }

    @Transactional
    public void deleteHashtag(Long id) {

        Hashtag hashtag = hashtagRepository.findById(id)
                .orElseThrow(() -> new NotFoundHashtagException("해시태그를 찾을 수 없습니다."));

        hashtagRepository.delete(hashtag);
    }
}
