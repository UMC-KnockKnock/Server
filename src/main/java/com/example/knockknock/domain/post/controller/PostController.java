package com.example.knockknock.domain.post.controller;

import com.example.knockknock.domain.hashtag.dto.HashtagRegisterRequestDto;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.post.dto.request.*;
import com.example.knockknock.domain.post.service.PostService;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/post")
@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @Operation(summary = "게시판 글 작성 (토큰O)", description = "게시글 작성 - 사진은 멀티파트로, request에 null값 있으면 안됨")
    @PostMapping("/create")
    public ResponseEntity createPost(
            @RequestPart("request")
            @Valid
            PostCreateRequestDto request,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(required = false) List<MultipartFile> images)
    {
        postService.createPost(request, images, userDetails);
        return ResponseMessage.SuccessResponse("게시글 작성 완료", "");
    }

    @Operation(summary = "특정 게시글 좋아요 수, 댓글 수 (토큰X)", description = "특정 게시글 좋아요 수, 댓글 수를 불러오기")
    @GetMapping("/{postId}/details")
    public ResponseEntity getPostDetails(
            @PathVariable Long postId
    ) {
        return new ResponseEntity<>(postService.getPostDetail(postId), HttpStatus.OK);
    }

    @Operation(summary = "내가 쓴 글 (토큰O)", description = "현재 로그인 한 회원이 작성한 게시글들 불러오기")
    @GetMapping("/myPosts")
    public ResponseEntity getMyPosts(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(postService.getMyPosts(userDetails), HttpStatus.OK);
    }

    @Operation(summary = "작성자와 비교 동작 (토큰O)", description = "현재 접근한 게시글이 내가 쓴 게시글인지 확인")
    @PostMapping("/{postId}/verification")
    public ResponseEntity isMyPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Boolean isVerified = postService.isMyPost(postId, userDetails);
        if(isVerified){
            return ResponseMessage.SuccessResponse("작성자와 일치합니다.", "");
        } else return ResponseMessage.ErrorResponse(GlobalErrorCode.PERMISSION_DENIED);
    }

    @Operation(summary = "게시글 수정 (토큰X)", description = "게시글 수정 - imagesToDelete에 삭제할 이미지 Url 배열에 담아 입력")
    @PutMapping("/{postId}")
    public ResponseEntity updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto request,
            @RequestPart(required = false) List<MultipartFile> images
    ) {
        postService.updatePost(postId, request, images);
        return ResponseMessage.SuccessResponse("게시글 수정 완료", "");
    }

    @Operation(summary = "게시글 삭제 (토큰X)", description = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity deletePost(
            @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return ResponseMessage.SuccessResponse("게시글 삭제 완료", "");
    }

    @Operation(summary = "게시글 공유 (토큰X)", description = "공유할 게시글의 url 반환")
    @GetMapping("/{postId}/share")
    public ResponseEntity<String> sharePost(
            @PathVariable Long postId, HttpServletRequest request
    ) {
        return new ResponseEntity<>(postService.sharePost(postId, request), HttpStatus.OK);
    }
}
