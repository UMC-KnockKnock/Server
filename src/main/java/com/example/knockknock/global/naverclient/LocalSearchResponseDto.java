package com.example.knockknock.global.naverclient;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSearchResponseDto {
    private Double locationMapx;  // 사용자가 입력한 location의 x 좌표
    private Double locationMapy;  // 사용자가 입력한 location의 y 좌표
    private List<LocalSearchItem> items; // XML 포멧에서는 item 태그로, JSON 포멧에서는 items 속성으로 표현된다. 개별 검색 결과이며 title, link, description, address, mapx, mapy를 포함한다.

}
