package com.example.knockknock.service;

import com.example.knockknock.domain.model.*;
import com.example.knockknock.domain.repository.*;
import com.example.knockknock.dto.*;
import com.example.knockknock.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private PostRepository postRepository;
    private BoardRepository boardRepository;
    private LikeRepository likeRepository;
    private CommentRepository commentRepository;
    private HashtagRepository hashtagRepository;
    private UserService userService;

    @Transactional
    public void createPost(PostCreateRequestDto request) {
        User user = userService.findUserFromToken();
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));

        postRepository.save(Post.builder()
                .user(user)
                .board(board)
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(0)
                .build());

    }

    @Transactional
    public List<GetPostListResponseDto> getPostsByBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new NotFoundBoardException("게시판을 찾을 수 없습니다."));
        List<Post> posts = postRepository.findByBoard(board);
        return posts.stream()
                .map(GetPostListResponseDto::from)
                .collect(Collectors.toList());
    }


    @Transactional
    public PostDetailResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id)
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
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        postRepository.delete(post);
    }

    @Transactional
    public void likePost(Long id) {
        User user = userService.findUserFromToken();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        Optional<Like> likeOptional = likeRepository.findByUserAndPost(user, post);

        if (likeOptional.isPresent() && likeOptional.get().isLiked()) {
            throw new AlreadyLikeException("이미 좋아요한 게시물입니다.");
        }

        if (likeOptional.isEmpty()) {
            likeRepository.save(Like.builder()
                    .user(user)
                    .post(post)
                    .isLiked(true)
                    .build());
        } else {
            likeOptional.get().like();
        }
        post.addLike();
    }

    @Transactional
    public void deletePostLike(Long id) {
        User user = userService.findUserFromToken();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new NotFoundLikeException("좋아요 정보를 찾을 수 없습니다."));

        if (!like.isLiked()) {
            throw new AlreadyDeleteLikeException("이미 좋아요가 취소된 게시글입니다.");
        }
        post.removeLike();
        like.disLike();
    }

    @Transactional
    public CommentRegisterResponseDto registerComment(Long id, CommentRegisterRequestDto request) {
        User user = userService.findUserFromToken();

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        commentRepository.save(Comment.builder()
                .user(user)
                .post(post)
                .content(request.getContent())
                .build());
        return new CommentRegisterResponseDto(request.getContent());
    }

    @Transactional
    public List<GetCommentListResponseDto> getComments(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream()
                .map(GetCommentListResponseDto::from)
                .collect(Collectors.toList());
    }
    @Transactional
    public void updateComment(Long id, CommentUpdateRequestDto request) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundCommentException("댓글을 찾을 수 없습니다."));

        comment.updateComment(request.getContent());
    }

    @Transactional
    public void deleteComment(Long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundCommentException("댓글을 찾을 수 없습니다."));

        commentRepository.delete(comment);
    }

    @Transactional
    public GetLikeCountResponseDto getLikeCount(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        return GetLikeCountResponseDto.builder()
                .likeCount(post.getLikeCount())
                .build();
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
