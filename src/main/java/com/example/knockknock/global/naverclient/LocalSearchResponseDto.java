package com.example.knockknock.global.naverclient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSearchResponseDto {
    private String lastBuildDate; // 검색 결과를 생성한 시간이다.
    private int total; //  검색 결과 문서의 총 개수를 의미한다.
    private int start; // 검색 결과 문서 중, 문서의 시작점을 의미한다.
    private int display; // 검색된 검색 결과의 개수이다.
    private List<LocalSearchItem> items; // XML 포멧에서는 item 태그로, JSON 포멧에서는 items 속성으로 표현된다. 개별 검색 결과이며 title, link, description, address, mapx, mapy를 포함한다.

}
