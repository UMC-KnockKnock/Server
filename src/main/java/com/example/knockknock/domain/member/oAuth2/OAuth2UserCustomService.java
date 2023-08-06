package com.example.knockknock.domain.member.oAuth2;

import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {

    private final  Map<String, Object> attributes;
    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        saveOrUpdate(oAuth2User);
        return oAuth2User;
    }

    private Member saveOrUpdate(OAuth2User oAuth2User) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                    return Member.builder()
                        .email((String) response.get("email"))
                        .nickName((String) response.get("nickname"))
                        .age((Integer) response.get("age"))
                        .memberName((String) response.get("name"))
                        .birthday((String) response.get("birthday"))
                            .build();



    }
}
