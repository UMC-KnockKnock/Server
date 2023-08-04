package com.example.knockknock.domain.member.controller;

import com.example.knockknock.domain.member.dto.MemberSignupDto;
import com.example.knockknock.domain.member.dto.MemberUpdateDto;
import com.example.knockknock.domain.member.dto.MemberWithdraw;
import com.example.knockknock.domain.member.dto.UpdatePasswordDto;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@Slf4j
@RestController()
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody MemberSignupDto signUpDto) throws Exception{
        log.info("회원 가입 확인 중 : " + signUpDto.getEmail());
        memberService.signup(signUpDto);
    return new ResponseEntity<>(HttpStatus.OK);
    }



    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateInfo(@Valid @RequestBody MemberUpdateDto memberUpdateDto) throws Exception{
        log.info(" 정보 변경 중 입니다 : " + memberUpdateDto.email());
        memberService.update(memberUpdateDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/updatepassword")
    public ResponseEntity updatePassword(@Valid  @RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
        memberService.updatePassword(updatePasswordDto.getCheckPassword(), updatePasswordDto.getToBePassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("member/{id}")
    public ResponseEntity getInfo(@Valid @PathVariable("id") Long id) throws Exception {
        Member info = memberService.getInfo(id);

        return new ResponseEntity<>(info, HttpStatus.OK);
    }

    @GetMapping("member")
    public ResponseEntity getMyInfo(HttpResponse httpResponse) throws Exception{
        Member myInfo = memberService.getMyInfo();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/member")
    public ResponseEntity withdraw(@Valid @RequestBody MemberWithdraw memberWithdraw) throws Exception {

        memberService.withdraw(memberWithdraw.getCheckPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    이메일 로 임시비밀번호 전달

    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("memberEmail") String memberEmail){
        ms.
    } */






}
