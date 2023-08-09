package com.example.knockknock.domain.gathering.service;

import com.example.knockknock.global.naverclient.LocalSearchRequestDto;
import com.example.knockknock.global.naverclient.LocalSearchResponseDto;
import com.example.knockknock.global.naverclient.NaverClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverSearchService {
    private final NaverClient naverClient;

    public LocalSearchResponseDto searchLocal(LocalSearchRequestDto request) {
        return naverClient.localSearch(request);
    }
}
