package com.example.knockknock.domain.member.oAuth2;

import java.util.Map;

public class KakkoOAuth2Info extends OAuthMemberInfo {


    public KakkoOAuth2Info(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getNickName() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if(profile == null){
            throw new IllegalArgumentException("카카오 회원 정보가 없습니다");
        }
        return (String) profile.get("nickName");
    }

    @Override
    public String name() {
        Map<String, Object> account = (Map<String, Object>) attributes.get("account");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");

        if (profile == null){
            throw new IllegalArgumentException("카카오 회원정보가 없습니다");
        }
        return (String) profile.get("name");
    }
}
