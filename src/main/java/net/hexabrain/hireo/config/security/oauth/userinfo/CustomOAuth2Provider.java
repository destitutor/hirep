package net.hexabrain.hireo.config.security.oauth.userinfo;

public enum CustomOAuth2Provider {
    KAKAO("kakao"),
    NAVER("naver");

    private final String provider;

    CustomOAuth2Provider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public boolean equalsWith(String provider) {
        return this.provider.equalsIgnoreCase(provider);
    }
}
