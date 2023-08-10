package com.example.knockknock.global.oauth2;

import com.example.knockknock.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

/**
 * 네이버에서 받아오는 데이터를 처리하는 DTO 클래스
 */
@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo; // 네이버 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    /**
     * OAuthAttributes 객체 반환
     * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
     * 네이버 로그인 API에서 제공하는 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
     */
    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    /**
     * ofNaver 메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 주입된 상태
     * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl을 가져와서 build
     */
    public Member toEntity(OAuth2UserInfo oauth2UserInfo) {
        String email = oauth2UserInfo.getEmail();
        if(email == null || email.isEmpty()) {
            email = UUID.randomUUID().toString() + "@example.com"; // 여기서 "@example.com"는 임의의 도메인으로 설정했습니다.
        }
        String birthYear = oauth2UserInfo.getBirthYear();
        String birthDay = oauth2UserInfo.getBirthday();
        String birthDate = "";
        if(birthYear != null && birthDay != null){
        // 마지막 2자리를 취하여 YYMMDD 형태로 변환
            birthDate = birthYear.substring(birthYear.length() - 2) + birthDay.replace("-", "");
        }

        Member member = Member.builder()
                .memberName(oauth2UserInfo.getName())
                .memberGender(oauth2UserInfo.getGender())
                .socialId(oauth2UserInfo.getId())
                .nickName(oauth2UserInfo.getNickname())
                .birthday(birthDate)
                .email(email)
                .profileImageURL(oauth2UserInfo.getImageUrl())
                .build();
        member.calculateAge();
        return member;
    }
}