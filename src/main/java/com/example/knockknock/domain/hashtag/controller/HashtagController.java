package com.example.knockknock.domain.hashtag.controller;

import com.example.knockknock.domain.hashtag.dto.HashtagRegisterRequestDto;
import com.example.knockknock.domain.hashtag.service.HashtagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/hashtag")
@RequiredArgsConstructor
@RestController
public class HashtagController {
    private final HashtagService hashtagService;
    @PostMapping("/{postId}")
    public ResponseEntity addHashtag(
            @PathVariable Long postId,
            @RequestBody HashtagRegisterRequestDto request
    ) {
        hashtagService.addHashtag(postId, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{hashtagId}/delete")
    public ResponseEntity deleteHashtag(
            @PathVariable Long hashtagId
    ) {
        hashtagService.deleteHashtag(hashtagId);
        return ResponseEntity.ok().build();
    }
}
