package com.example.knockknock.domain.bestfriend.controller;

import com.example.knockknock.domain.bestfriend.service.BestFriendService;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.message.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bestfriend")
public class BestFriendController {
    private final BestFriendService bestFriendService;

    @Operation(summary = "찐친 관리 - 찐친 목록 (토큰O)", description = "찐친 목록 불러오기")
    @GetMapping()
    public ResponseEntity getBestFriends(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new ResponseEntity<>(bestFriendService.getBestFriends(userDetails), HttpStatus.OK);
    }
}
