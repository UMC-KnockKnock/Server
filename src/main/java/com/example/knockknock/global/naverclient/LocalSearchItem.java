package com.example.knockknock.global.naverclient;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocalSearchItem {
    private String title;  // 검색 결과 업체, 기관명을 나타낸다.
    private String category; // 검색 결과 업체, 기관의 분류 정보를 제공한다.
    private String address;  // 검색 결과 업체, 기관명의 주소를 제공한다.
    private String roadAddress;  // 검색 결과 업체, 기관명의 도로명 주소를 제공한다.
    private int mapx;  // 검색 결과 업체, 기관명 위치 정보의 x좌표를 제공한다. 제공값은 카텍좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.
    private int mapy;  // 검색 결과 업체, 기관명 위치 정보의 y좌표를 제공한다. 제공값은 카텍 좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.
}
