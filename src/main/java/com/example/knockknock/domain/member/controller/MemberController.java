package com.example.knockknock.domain.member.controller;

import com.example.knockknock.domain.member.dto.request.*;
import com.example.knockknock.domain.member.dto.response.GetMembersResponseDto;
import com.example.knockknock.domain.member.dto.response.MemberDetailResponseDto;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberService;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.message.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PostMapping("/nickname")
    public ResponseEntity checkNickName(
            @RequestBody NicknameCheckRequestDto request){
        memberService.checkNickname(request);
        return ResponseMessage.SuccessResponse("사용 가능한 닉네임입니다.", "");
    }


    @PostMapping("/signup")
    public ResponseEntity signup(
            @RequestPart MemberSignUpRequestDto request,
            @RequestPart(required = false) MultipartFile profileImage){
        memberService.signup(request, profileImage);
        return ResponseMessage.SuccessResponse("회원가입 성공", "");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return ResponseMessage.SuccessResponse("로그인 성공", "");
    }

    @GetMapping("/socialLogin")
    public void socialLogin(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("/oauth2/authorization/naver");
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request) {

        return ResponseMessage.SuccessResponse("토큰 재발급 성공", "new accessToken: " + memberService.reissue(request));

    }

    @PostMapping("/emailcode")
    public ResponseEntity authentication(@RequestBody EmailAuthenticationRequestDto request) {
        memberService.sendCode(request);
        return ResponseMessage.SuccessResponse("인증메일을 발송하였습니다", "");
    }

    @PostMapping("/authentication")
    public ResponseEntity isAuthenticated (@RequestBody CheckAuthCodeRequestDto request) {
        Boolean isAuthenticated = memberService.isValid(request);
        if (isAuthenticated){
        return ResponseMessage.SuccessResponse("인증이 완료되었습니다.", "");
        } else return ResponseMessage.ErrorResponse(GlobalErrorCode.INVALID_CODE);
    }

    @GetMapping()
    public ResponseEntity<MemberDetailResponseDto> getMemberDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return new ResponseEntity<>(memberService.getMemberDetail(userDetails), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetMembersResponseDto>> getAllMembers() {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MemberUpdateRequestDto request,
            @RequestPart(required = false) MultipartFile profileImage
    ) {
        memberService.updateMember(userDetails, request, profileImage);
        return ResponseMessage.SuccessResponse("수정 완료", "");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        memberService.deleteMember(userDetails);
        return ResponseMessage.SuccessResponse("삭제 완료", "");

    }
}
