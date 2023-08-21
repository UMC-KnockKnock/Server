package com.example.knockknock.global.naverclient;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NaverClient {

    // @Value 어노테이션을 사용
    @Value("${naver.localsearch.client-id}")
    private String naverClientId;

    @Value("${naver.localsearch.client-secret}")
    private String naverSecret;

    @Value("${naver.localsearch.url}")
    private String naverLocalSearchUrl;

    private final NaverGeocodingClient naverGeocodingClient;

    public LocalSearchResponseDto localSearch(LocalSearchRequestDto request) {
        var uri = UriComponentsBuilder
                .fromUriString(naverLocalSearchUrl)
                .queryParams(request.toMultiValueMap())
                .build()
                .encode()
                .toUri();
        var headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(headers);
        var responseType = new ParameterizedTypeReference<LocalSearchResponseDto>(){};


        var responseEntity = new RestTemplate()
                .exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        responseType
                );

        LocalSearchResponseDto responseDto = responseEntity.getBody();

        if (responseDto != null && responseDto.getItems() != null) {
            for (LocalSearchItem item : responseDto.getItems()) {
                GeocodingResponse geocodingResponse = naverGeocodingClient.geocode(item.getAddress());

                if (geocodingResponse != null && geocodingResponse.getAddresses() != null && !geocodingResponse.getAddresses().isEmpty()) {
                    GeocodingResponse.Address address = geocodingResponse.getAddresses().get(0);
                    item.setMapx(Double.parseDouble(address.getX()));
                    item.setMapy(Double.parseDouble(address.getY()));
                }
            }
        }

        return responseDto;
    }
}
