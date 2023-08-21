package com.example.knockknock.global.naverclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class NaverGeocodingClient {
    @Value("${naver.geocoding.client-id}")
    private String naverClientId;

    @Value("${naver.geocoding.client-secret}")
    private String naverSecret;

    @Value("${naver.geocoding.url}")
    private String naverGeocodingUrl;

    public GeocodingResponse geocode(String address) {
        var uri = UriComponentsBuilder
                .fromUriString(naverGeocodingUrl)
                .queryParam("query", address)
                .build()
                .encode()
                .toUri();

        var headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", naverClientId);
        headers.set("X-NCP-APIGW-API-KEY", naverSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        var httpEntity = new HttpEntity<>(headers);

        ResponseEntity<GeocodingResponse> response = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                GeocodingResponse.class
        );

        return response.getBody();
    }
}
