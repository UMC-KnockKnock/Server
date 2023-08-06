package com.example.knockknock.global.oauth2;

import com.example.knockknock.domain.member.entity.Gender;

import java.util.Map;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        return (String) response.get("id");
    }

    @Override
    public String getEmail(){
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        return (String) response.get("email");
    }

    @Override
    public String getNickname() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("nickname");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("profile_image");
    }

    @Override
    public String getName() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }

        return (String) response.get("name");
    }

    @Override
    public Gender getGender() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        }
        if (response.get("gender").equals("M")) {
            return Gender.MALE;
        } else if (response.get("gender").equals("F")) {
            return Gender.FEMALE;
        } else {
            throw new IllegalArgumentException("Invalid gender: " + attributes.get("gender"));
        }

    }

    @Override
    public Integer getAge() {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        if (response == null) {
            return null;
        } else if(response.get("age") != null) {
            return Integer.parseInt((String) response.get("age"));
        }
        return null;

    }

}
