package com.example.knockknock.global.oauth2;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId(); //소셜 식별 값 : 네이버 - "id"
    public abstract String getName();
    public abstract Gender getGender();
    public abstract String getBirthday();
    public abstract String getBirthYear();

    public abstract String getNickname();

    public abstract String getEmail();

    public abstract String getImageUrl();
}
