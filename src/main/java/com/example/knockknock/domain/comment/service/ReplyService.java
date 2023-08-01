package com.example.knockknock.domain.comment.service;

import com.example.knockknock.domain.comment.dto.request.ReplyRegisterRequestDto;
import com.example.knockknock.domain.comment.dto.request.ReplyUpdateRequestDto;
import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.comment.entity.Reply;
import com.example.knockknock.domain.comment.repository.CommentRepository;
import com.example.knockknock.domain.comment.repository.ReplyRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberIsLoginService;
import com.example.knockknock.domain.post.dto.request.PostUpdateRequestDto;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final MemberIsLoginService memberIsLoginService;

    private final ReplyRepository replyRepository;

    @Transactional
    public void addReply(Long commentId, ReplyRegisterRequestDto request, UserDetailsImpl userDetails){
        Member member = memberIsLoginService.isLogin(userDetails);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.COMMENT_NOT_FOUND));

        Post post = postRepository.findById(comment.getPost().getPostId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.POST_NOT_FOUND));

        Reply reply = Reply.builder()
                .member(member)
                .post(post)
                .parentComment(comment)
                .isAnonymous(request.getIsAnonymous())
                .content(request.getContent())
                .build();

        comment.addReply(reply);
        replyRepository.save(reply);
    }

    @Transactional
    public void updateReply(Long replyId , ReplyUpdateRequestDto request, UserDetailsImpl userDetails) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.REPLY_NOT_FOUND));
        // 현재 로그인한 멤버 정보 가져오기
        Long currentMemberId = userDetails.getUser().getMemberId();

        // 작성자 정보 가져오기
        Long replyAuthorId = reply.getMember().getMemberId();

        if (!currentMemberId.equals(replyAuthorId)) {
            throw new GlobalException(GlobalErrorCode.PERMISSION_DENIED);
        }
        reply.updateReply(request.getContent());
    }

    @Transactional
    public void deleteReply(Long replyId, UserDetailsImpl userDetails) {

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.REPLY_NOT_FOUND));
        // 현재 로그인한 멤버 정보 가져오기
        Long currentMemberId = userDetails.getUser().getMemberId();

        // 게시글 작성자 정보 가져오기
        Long replyAuthorId = reply.getMember().getMemberId();

        if (!currentMemberId.equals(replyAuthorId)) {
            throw new GlobalException(GlobalErrorCode.PERMISSION_DENIED);
        } else{
        if (reply.getIsAnonymous()){
            reply.getParentComment().removeReply(reply);
        }

        replyRepository.delete(reply);
        }
    }
}
