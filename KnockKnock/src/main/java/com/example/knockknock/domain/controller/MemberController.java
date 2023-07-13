package com.example.knockknock.domain.controller;

import com.example.knockknock.global.dto.response.MemberDetailResponseDto;
import com.example.knockknock.global.dto.request.MemberSignUpRequestDto;
import com.example.knockknock.global.dto.request.MemberUpdateRequestDto;
import com.example.knockknock.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(
            @RequestBody MemberSignUpRequestDto request
    ) {
        memberService.createMember(request);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<MemberDetailResponseDto> getUserDetail(
            @PathVariable("userId") Long id
    ) {
        MemberDetailResponseDto userDetail = memberService.getMemberDetail(id);
        return ResponseEntity.ok(userDetail);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(
            @PathVariable("userId") Long id,
            @RequestBody MemberUpdateRequestDto request
    ) {
        memberService.updateMember(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable("userId") Long id
    ) {
        memberService.deleteMember(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
