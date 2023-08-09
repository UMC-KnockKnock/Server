package com.example.knockknock.domain.gathering.controller;

import com.example.knockknock.domain.gathering.service.NaverSearchService;
import com.example.knockknock.global.naverclient.LocalSearchRequestDto;
import com.example.knockknock.global.naverclient.LocalSearchResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/naver")
@RequiredArgsConstructor
public class NaverSearchController {

    private final NaverSearchService naverSearchService;

    @GetMapping("/search/local")
    public ResponseEntity<LocalSearchResponseDto> searchLocal(@RequestBody LocalSearchRequestDto request) {
        return ResponseEntity.ok(naverSearchService.searchLocal(request));
    }
}
