package com.example.knockknock.domain.member.controller;

import com.example.knockknock.domain.member.dto.request.*;
import com.example.knockknock.domain.member.dto.response.GetMembersResponseDto;
import com.example.knockknock.domain.member.dto.response.MemberDetailResponseDto;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.domain.member.service.MemberService;
import com.example.knockknock.global.exception.GlobalErrorCode;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "멤버 관련 서버 테스트 코드 - 프론트랑 상관x")
    @GetMapping("/")
    public ResponseEntity test(){
        memberService.test();
        return ResponseMessage.SuccessResponse("성공", "");
    }
    @Operation(summary = "닉네임 중복 여부 체크 (토큰X)", description = "회원가입 단계에서 닉네임 중복 확인")
    @PostMapping("/nickname")
    public ResponseEntity checkNickName(
            @RequestBody NicknameCheckRequestDto request){
        memberService.checkNickname(request);
        return ResponseMessage.SuccessResponse("사용 가능한 닉네임입니다.", "");
    }

    @Operation(summary = "회원가입 (토큰X)", description = "회원가입 요청 - 프로필 사진 멀티파트로 보내야 함")
    @PostMapping("/signup")
    public ResponseEntity signup(
            @RequestPart MemberSignUpRequestDto request,
            @RequestPart(required = false) MultipartFile profileImage){
        memberService.signup(request, profileImage);
        return ResponseMessage.SuccessResponse("회원가입 성공", "");
    }

    @Operation(summary = "로그인 (토큰X)", description = "아이디 비밀번호로 로그인 하기")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        memberService.login(loginRequestDto, response);
        return ResponseMessage.SuccessResponse("로그인 성공", "");
    }
    
    @Operation(summary = "로그아웃 (토큰O)", description = "리프레시 토큰을 헤더에 담아 보내면 삭제해줌으로써 로그아웃이 됨")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request
    ) {
        memberService.logout(request);
        return ResponseMessage.SuccessResponse("로그아웃 완료", "");
    }
    
    @Operation(summary = "네이버 로그인 (토큰X)", description = "네이버 로그인 요청 보내면 익숙한 네이버 로그인 화면 나옴 브라우저에서 테스트 더 잘 됨")
    @GetMapping("/socialLogin")
    public void socialLogin(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("/oauth2/authorization/naver");
    }

    @Operation(summary = "액세스 토큰 재요청 (토큰O)", description = "다른 동작과 다르게 리프레시 토큰을 헤더에 담아 보내서 새로운 액세스 토큰 발급")
    @GetMapping("/reissue")
    public ResponseEntity<?> reissue(HttpServletRequest request) {

        return ResponseMessage.SuccessResponse("토큰 재발급 성공", "new accessToken: " + memberService.reissue(request));

    }

    @Operation(summary = "회원가입 - 이메일 인증 동작 (토큰X)", description = "회원가입 요청 전 이메일 인증하기")
    @PostMapping("/emailcode")
    public ResponseEntity authentication(@RequestBody EmailAuthenticationRequestDto request) {
        memberService.sendCode(request);
        return ResponseMessage.SuccessResponse("인증메일을 발송하였습니다", "");
    }

    @Operation(summary = "이메일 인증 - 인증 코드 확인 동작 (토큰X)", description = "해당 이메일로 발송된 코드와 일치하는지 확인하기")
    @PostMapping("/authentication")
    public ResponseEntity isAuthenticated (@RequestBody CheckAuthCodeRequestDto request) {
        Boolean isAuthenticated = memberService.isValid(request);
        if (isAuthenticated){
        return ResponseMessage.SuccessResponse("인증이 완료되었습니다.", "");
        } else return ResponseMessage.ErrorResponse(GlobalErrorCode.INVALID_CODE);
    }

    @Operation(summary = "내 정보 (토큰O)", description = "현재 로그인 한 회원의 내 정보 불러 오기")
    @GetMapping()
    public ResponseEntity<MemberDetailResponseDto> getMemberDetail(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return new ResponseEntity<>(memberService.getMemberDetail(userDetails), HttpStatus.OK);
    }

    @Operation(summary = "모든 회원의 닉네임 + 고유 ID 목록(토큰X)", description = "혹시 필요할까 싶어서 만들어 둠")
    @GetMapping("/getAll")
    public ResponseEntity<List<GetMembersResponseDto>> getAllMembers() {
        return new ResponseEntity<>(memberService.getAllMembers(), HttpStatus.OK);
    }

    @Operation(summary = "내 정보 수정 (토큰O)", description = "내 정보 수정하기")
    @PutMapping("/update")
    public ResponseEntity updateUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart MemberUpdateRequestDto request,
            @RequestPart(required = false) MultipartFile profileImage
    ) {
        memberService.updateMember(userDetails, request, profileImage);
        return ResponseMessage.SuccessResponse("수정 완료", "");
    }

    @Operation(summary = "회원 탈퇴 (토큰O)", description = "회원 탈퇴")
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        memberService.deleteMember(userDetails);
        return ResponseMessage.SuccessResponse("삭제 완료", "");

    }
}
