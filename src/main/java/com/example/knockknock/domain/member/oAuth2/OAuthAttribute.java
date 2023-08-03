package com.example.knockknock.domain.member.oAuth2;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.entity.SocialType;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class OAuthAttribute {

    private String nameAttributeKey;
    private OAuthMemberInfo authMemberInfo;

    @Builder
    public OAuthAttribute(String nameAttributeKey, OAuthMemberInfo authMemberInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.authMemberInfo = authMemberInfo;
    }

    public static OAuthAttribute of(SocialType socialType, String memberAttributeName, Map<String, Object> attributes){
        if (socialType == SocialType.NAVER){
            return ofNaver(memberAttributeName, attributes );
        }
        if (socialType == SocialType.KAKKO){
            return ofKakko(memberAttributeName, attributes);
        }
        return ofGoogle(memberAttributeName, attributes);
        }

    private static OAuthAttribute ofGoogle(String memberAttributeName, Map<String, Object> attributes) {
    return OAuthAttribute.builder()
            .nameAttributeKey(memberAttributeName)
            .authMemberInfo(new GoogleOAuth2Info(attributes))
            .build();
    }





    private static OAuthAttribute ofKakko(String memberAttributeName, Map<String, Object> attributes) {
    return OAuthAttribute.builder()
            .nameAttributeKey(memberAttributeName)
            .authMemberInfo(new KakkoOAuth2Info(attributes))
            .build();
    }

    private static OAuthAttribute ofNaver(String memberAttributeName, Map<String, Object> attributes) {
        return OAuthAttribute.builder()
                .nameAttributeKey(memberAttributeName)
                .authMemberInfo(new NaverOAuth2Info(attributes))
                .build();
    }


    public Member toOAuth2Entity(SocialType socialType, OAuthMemberInfo oAuthMemberInfo){
        return Member.builder()
                .socialType(socialType)
                .socialId(oAuthMemberInfo.getId())
                .email(UUID.randomUUID() + "@socialMember.com")
                .memberName(authMemberInfo.name())
                .nickName(authMemberInfo.getNickName())
                .build();

    }
}
