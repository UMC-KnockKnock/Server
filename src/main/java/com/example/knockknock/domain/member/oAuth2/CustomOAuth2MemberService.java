package com.example.knockknock.domain.member.oAuth2;

import com.example.knockknock.domain.member.MemberRepository;
import com.example.knockknock.domain.member.entity.Member;
import com.example.knockknock.domain.member.entity.SocialType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    private static final String NAVER = "naver";
    public static final String KAKKO = "kakko";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        SocialType socialType = getSocialType(registrationId);
        String memberNameAttibuteName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> extractAttributes = oAuth2User.getAttributes();

        OAuthAttribute extractAuttribute = OAuthAttribute.of(socialType, memberNameAttibuteName, extractAttributes);
        Member createMember = getMember(extractAuttribute, socialType);

        return oAuth2User;

    }

    private Member getMember(OAuthAttribute authAttribute, SocialType socialType) {
        Member findBySocial = memberRepository.findBySocialTypeAndSocialId(socialType, authAttribute.getAuthMemberInfo().getId()).orElse(null);

        if (findBySocial == null ){
            return saveMember(authAttribute, socialType);
        }
        return findBySocial;
    }

    private Member saveMember(OAuthAttribute authAttribute, SocialType socialType) {
        Member save = authAttribute.toOAuth2Entity(socialType, authAttribute.getAuthMemberInfo());
        return memberRepository.save(save);
    }

    private SocialType getSocialType(String registrationId) {
        if(NAVER.equals(registrationId)){
            return SocialType.NAVER;
        }
        if (KAKKO.equals(registrationId)){
            return SocialType.KAKKO;
        }
        return SocialType.GOOGLE;
    }
}
