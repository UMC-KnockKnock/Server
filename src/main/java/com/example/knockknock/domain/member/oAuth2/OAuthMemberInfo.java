package com.example.knockknock.domain.member.oAuth2;

import java.util.Map;

public abstract class OAuthMemberInfo {

    protected Map<String, Object> attributes;

    public OAuthMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;

    }
    public abstract String getId();

    public abstract  String getNickName();

    public abstract String name();

}
