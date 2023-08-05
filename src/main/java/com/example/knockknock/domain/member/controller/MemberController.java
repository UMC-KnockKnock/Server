package com.example.knockknock.domain.member.controller;

import com.example.knockknock.domain.member.dto.request.EmailAuthenticationRequestDto;
import com.example.knockknock.domain.member.dto.request.LoginRequestDto;
import com.example.knockknock.domain.member.dto.request.MemberSignUpRequestDto;
import com.example.knockknock.domain.member.dto.request.MemberUpdateRequestDto;
import com.example.knockknock.domain.member.dto.response.GetMembersResponseDto;
import com.example.knockknock.domain.member.dto.response.MemberDetailResponseDto;
import com.example.knockknock.domain.member.service.MemberService;
import com.example.knockknock.global.message.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/member")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseEntity test(){
        memberService.test();
        return ResponseMessage.SuccessResponse("성공", "");
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
            @RequestBody MemberSignUpRequestDto request,
            @RequestPart(required = false) MultipartFile profileImage){
        memberService.signup(request,profileImage);
        return ResponseMessage.SuccessResponse("회원가입 성공", "");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return ResponseMessage.SuccessResponse("로그인 성공", "");
    }

    @PostMapping("/authentication")
    public ResponseEntity authentication(@RequestBody EmailAuthenticationRequestDto request) {
        memberService.authentication(request);
        return ResponseMessage.SuccessResponse("인증메일을 발송하였습니다", "");
    }

    @GetMapping("/get/{memberId}")
    public ResponseEntity<MemberDetailResponseDto> getMemberDetail(
            @PathVariable Long memberId
    ) {
        MemberDetailResponseDto memberDetail = memberService.getMemberDetail(memberId);
        return ResponseEntity.ok(memberDetail);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetMembersResponseDto>> getAllMembers() {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @PutMapping("/update/{memberId}")
    public ResponseEntity updateUser(
            @PathVariable Long memberId,
            @RequestBody MemberUpdateRequestDto request,
            @RequestPart(required = false) MultipartFile profileImage
    ) {
        memberService.updateMember(memberId, request, profileImage);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{memberId}")
    public ResponseEntity deleteUser(
            @PathVariable Long memberId
    ) {
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
