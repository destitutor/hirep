package net.hexabrain.hireo.config.security.oauth.userinfo;

import java.util.Map;

import static net.hexabrain.hireo.utils.ConvertUtils.uncheckedCast;

public class NaverOAuth2UserInfo extends OAuth2UserInfo {
    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public OAuth2Provider getProvider() {
        return OAuth2Provider.NAVER;
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
        Map<String, Object> naverAccount = uncheckedCast(attributes.get("response"));
        email = naverAccount.get("email").toString();
        name = naverAccount.get("name").toString();
    }
}
