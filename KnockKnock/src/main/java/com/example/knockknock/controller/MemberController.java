package com.example.knockknock.controller;

import com.example.knockknock.dto.MemberDetailResponseDto;
import com.example.knockknock.dto.MemberSignUpRequestDto;
import com.example.knockknock.dto.MemberUpdateRequestDto;
import com.example.knockknock.service.MemberService;
import jakarta.validation.Valid;
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
        memberService.createUser(request);
        return ResponseEntity.created(null).build();
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<MemberDetailResponseDto> getUserDetail(
            @PathVariable("userId") Long id
    ) {
        MemberDetailResponseDto userDetail = memberService.getUserDetail(id);
        return ResponseEntity.ok(userDetail);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity updateUser(
            @PathVariable("userId") Long id,
            @RequestBody MemberUpdateRequestDto request
    ) {
        memberService.updateUser(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable("userId") Long id
    ) {
        memberService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
