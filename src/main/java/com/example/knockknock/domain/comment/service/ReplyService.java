package com.example.knockknock.domain.comment.service;

import com.example.knockknock.domain.comment.dto.CommentRegisterResponseDto;
import com.example.knockknock.domain.comment.dto.ReplyRegisterRequestDto;
import com.example.knockknock.domain.comment.entity.Comment;
import com.example.knockknock.domain.comment.entity.Reply;
import com.example.knockknock.domain.comment.repository.CommentRepository;
import com.example.knockknock.domain.comment.repository.ReplyRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import com.example.knockknock.domain.post.entity.Post;
import com.example.knockknock.domain.post.repository.PostRepository;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {

    private CommentRepository commentRepository;
    private MemberRepository memberRepository;
    private PostRepository postRepository;

    private ReplyRepository replyRepository;

    public ReplyService(CommentRepository commentRepository, MemberRepository memberRepository, PostRepository postRepository, ReplyRepository replyRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
        this.replyRepository = replyRepository;
    }

    @Transactional
    public void addReply(Long commentId, ReplyRegisterRequestDto request){
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new GlobalException(GlobalErrorCode.MEMBER_NOT_FOUND));

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
}
