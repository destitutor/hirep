package net.hexabrain.hireo.config.security.oauth.userinfo;

public enum OAuth2Provider {
    KAKAO("kakao"),
    NAVER("naver");

    private final String provider;

    OAuth2Provider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public boolean equalsWith(String provider) {
        return this.provider.equalsIgnoreCase(provider);
    }
}
