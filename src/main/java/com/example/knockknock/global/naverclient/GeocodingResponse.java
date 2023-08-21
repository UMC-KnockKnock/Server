package com.example.knockknock.global.naverclient;

import lombok.Getter;

import java.util.List;
@Getter

public class GeocodingResponse {
    private String status;
    private List<Address> addresses;

    @Getter
    public static class Address {
        private String x;
        private String y;

    }
}
