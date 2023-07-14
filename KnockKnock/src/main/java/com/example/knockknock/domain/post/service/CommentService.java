package com.example.knockknock.domain.post.service;

import com.example.knockknock.domain.comment.dto.CommentRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.CommentRegisterResponseDto;
import com.example.knockknock.domain.comment.dto.CommentUpdateRequestDto;
import com.example.knockknock.domain.comment.dto.GetCommentsResponseDto;
import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.comment.repository.CommentRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostLikeRepository;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.NotFoundCommentException;
import com.example.knockknock.global.exception.NotFoundMemberException;
import com.example.knockknock.global.exception.NotFoundPostException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private MemberRepository memberRepository;
    private PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public CommentRegisterResponseDto registerComment(Long id, CommentRegisterRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new NotFoundMemberException("사용자를 찾을 수 없습니다."));;

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));

        commentRepository.save(Comment.builder()
                .member(member)
                .post(post)
                .isAnonymous(request.getIsAnonymous())
                .content(request.getContent())
                .build());
        return new CommentRegisterResponseDto(request.getContent());
    }

    @Transactional
    public List<GetCommentsResponseDto> getComments(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new NotFoundPostException("게시글을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream()
                .map(GetCommentsResponseDto::from)
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
}
