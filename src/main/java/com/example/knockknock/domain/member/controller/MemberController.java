package com.example.knockknock.domain.member.controller;

import com.example.knockknock.domain.member.dto.MemberSignupDto;
import com.example.knockknock.domain.member.dto.MemberUpdateDto;
import com.example.knockknock.domain.member.dto.MemberWithdraw;
import com.example.knockknock.domain.member.dto.UpdatePasswordDto;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController()
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody MemberSignupDto signUpDto) throws Exception{
        memberService.signup(signUpDto);
    return new ResponseEntity<>(HttpStatus.OK);
    }



    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity updateInfo(@Valid @RequestBody MemberUpdateDto memberUpdateDto) throws Exception{
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







}
