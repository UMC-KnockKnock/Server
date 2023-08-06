package com.example.knockknock.domain.comment.service;

import com.example.knockknock.domain.comment.dto.request.CommentRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.request.CommentUpdateRequestDto;
import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.comment.repository.CommentRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberIsLoginService memberIsLoginService;
    private final PostRepository postRepository;


    @Transactional
    public void registerComment(Long postId, CommentRegisterRequestDto request, UserDetailsImpl userDetails) {
        Member member = memberIsLoginService.isLogin(userDetails);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        Comment comment = Comment.builder()
                .member(member)
                .post(post)
                .isAnonymous(request.getIsAnonymous())
                .content(request.getContent())
                .build();

        // Post에 댓글 추가
        post.addComment(comment);

        // Comment 저장
        commentRepository.save(comment);
    }


    @Transactional
    public void updateComment(Long commentId, CommentUpdateRequestDto request, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));

        // 현재 로그인한 멤버 정보 가져오기
        Long currentMemberId = userDetails.getUser().getMemberId();

        // 작성자 정보 가져오기
        Long commentAuthorId = comment.getMember().getMemberId();

        if (!currentMemberId.equals(commentAuthorId)) {
            throw new GlobalException(GlobalErrorCode.PERMISSION_DENIED);
        }

        comment.updateComment(request.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));
        // 현재 로그인한 멤버 정보 가져오기
        Long currentMemberId = userDetails.getUser().getMemberId();

        // 게시글 작성자 정보 가져오기
        Long commentAuthorId = comment.getMember().getMemberId();

        if (!currentMemberId.equals(commentAuthorId)) {
            throw new GlobalException(GlobalErrorCode.PERMISSION_DENIED);
        } else{

        if (comment.getIsAnonymous()){
            comment.getPost().removeComment(comment);
        }

        commentRepository.delete(comment);
        }
    }
}
