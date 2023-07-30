package com.example.knockknock.domain.member.controller;

import com.example.knockknock.domain.member.dto.*;
import com.example.knockknock.domain.member.service.MemberService;
import com.example.knockknock.global.message.ResponseMessage;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> signup(@RequestBody MemberSignUpRequestDto request){
        memberService.signup(request);
        return ResponseMessage.SuccessResponse("회원가입 성공", "");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return ResponseMessage.SuccessResponse("로그인 성공", "");
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
            @RequestBody MemberUpdateRequestDto request
    ) {
        memberService.updateMember(memberId, request);
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
