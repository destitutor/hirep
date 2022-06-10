package net.hexabrain.hireo.config.security.oauth.userinfo;

import java.util.Map;

import static net.hexabrain.hireo.utils.ConvertUtils.uncheckedCast;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.KAKAO;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void initProperties() {
        Map<String, Object> kakaoAccount = uncheckedCast(attributes.get("kakao_account"));
        email = kakaoAccount.get("email").toString();
        name = kakaoAccount.get("name").toString();
    }
}