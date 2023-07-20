package com.example.knockknock.domain.comment.service;

import com.example.knockknock.domain.comment.dto.CommentRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.CommentRegisterResponseDto;
import com.example.knockknock.domain.comment.dto.CommentUpdateRequestDto;
import com.example.knockknock.domain.comment.dto.GetCommentsResponseDto;
import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.comment.repository.CommentRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.*;
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
    public CommentRegisterResponseDto registerComment(Long postId, CommentRegisterRequestDto request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        commentRepository.save(Comment.builder()
                .member(member)
                .post(post)
                .isAnonymous(request.getIsAnonymous())
                .content(request.getContent())
                .build());
        return new CommentRegisterResponseDto(request.getContent());
    }

    @Transactional
    public List<GetCommentsResponseDto> getComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));
        List<Comment> comments = commentRepository.findByPost(post);
        return comments.stream()
                .map(GetCommentsResponseDto::from)
                .collect(Collectors.toList());
    }
    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequestDto request) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));

        comment.updateComment(request.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));

        commentRepository.delete(comment);
    }
}
