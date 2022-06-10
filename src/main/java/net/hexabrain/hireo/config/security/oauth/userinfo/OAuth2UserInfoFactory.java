package net.hexabrain.hireo.config.security.oauth.userinfo;

import java.util.Map;

public class OAuth2UserInfoFactory {
    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("인스턴스화 할 수 없습니다.");
    }

    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        if (OAuth2Provider.KAKAO.equalsWith(registrationId)) {
            return new KakaoOAuth2UserInfo(attributes);
        } else if (OAuth2Provider.NAVER.equalsWith(registrationId)) {
            return new NaverOAuth2UserInfo(attributes);
        } else {
            throw new IllegalArgumentException(String.format("프로바이더 [%s]는 지원하지 않습니다.", registrationId));
        }
    }
}
