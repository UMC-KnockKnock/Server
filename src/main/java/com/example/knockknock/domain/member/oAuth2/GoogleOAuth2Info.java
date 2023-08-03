package com.example.knockknock.domain.member.oAuth2;

import java.util.Map;

public class GoogleOAuth2Info extends OAuthMemberInfo{

    public GoogleOAuth2Info(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getNickName() {
        return (String) attributes.get("nickName");
    }

    @Override
    public String name() {
        return (String) attributes.get("name");
    }
}
