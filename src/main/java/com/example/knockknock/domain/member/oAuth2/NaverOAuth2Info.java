package com.example.knockknock.domain.member.oAuth2;

import java.util.Map;

public class NaverOAuth2Info extends OAuthMemberInfo{

    public NaverOAuth2Info(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null){
            throw  new IllegalArgumentException("네이버 회원 정보가 없습니다");
        }
        return (String) response.get("id");
    }

    @Override
    public String getNickName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null){
            throw  new IllegalArgumentException("네이버 회원 정보가 없습니다");
        }
        return (String) response.get("nickname");
    }

    @Override
    public String name() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null){
            throw  new IllegalArgumentException("네이버 회원 정보가 없습니다");
        }
        return (String) response.get("name");
    }
}
