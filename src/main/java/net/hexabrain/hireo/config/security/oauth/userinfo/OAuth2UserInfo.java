package net.hexabrain.hireo.config.security.oauth.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected final Map<String, Object> attributes;

    protected String email;
    protected String name;

    protected OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        initProperties();
    }

    /**
     * 기본 인증 정보를 초기화합니다.
     * 생성자에서 자동으로 이 메서드를 호출합니다.
     */
    public abstract void initProperties();

    public abstract OAuth2Provider getProvider();

    public abstract String getEmail();

    public abstract String getName();
}
