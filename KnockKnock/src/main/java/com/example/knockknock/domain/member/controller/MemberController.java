package com.example.knockknock.domain.member.controller;

import com.example.knockknock.domain.member.dto.GetMembersResponseDto;
import com.example.knockknock.domain.member.dto.MemberDetailResponseDto;
import com.example.knockknock.domain.member.service.MemberService;
import com.example.knockknock.domain.member.dto.MemberSignUpRequestDto;
import com.example.knockknock.domain.member.dto.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
