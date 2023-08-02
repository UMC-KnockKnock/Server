package com.example.knockknock.domain.bestfriend.controller;

import com.example.knockknock.domain.bestfriend.service.BestFriendService;
import com.example.knockknock.domain.member.security.UserDetailsImpl;
import com.example.knockknock.global.message.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bestfriend")
public class BestFriendController {
    private final BestFriendService bestFriendService;

    @GetMapping()
    public ResponseEntity getBestFriends(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseMessage.SuccessResponse("불러오기 성공", bestFriendService.getBestFriends(userDetails));
    }

    @PostMapping("/add")
    public ResponseEntity addToBestFriend(
            @RequestBody List<Long> friendIds,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        bestFriendService.addToBestFriend(friendIds, userDetails);
        return ResponseMessage.SuccessResponse("찐친으로 추가하였습니다.", "");
    }
}
