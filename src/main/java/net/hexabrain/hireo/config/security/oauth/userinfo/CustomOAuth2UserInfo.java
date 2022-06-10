package net.hexabrain.hireo.config.security.oauth.userinfo;

import org.jetbrains.annotations.Contract;

import java.util.Map;

public abstract class CustomOAuth2UserInfo {
    protected final Map<String, Object> attributes;

    protected String email;
    protected String name;

    protected CustomOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        initProperties();
    }

    /**
     * 기본 인증 정보를 초기화합니다.
     * 생성자에서 자동으로 이 메서드를 호출합니다.
     */
    public abstract void initProperties();

    /**
     * 인증 정보에서 프로바이더를 반환합니다.
     * @return 프로바이더 (null이 아님)
     */
    @Contract("->!null")
    public abstract CustomOAuth2Provider getProvider();

    /**
     * 인증 정보에서 이메일을 반환합니다.
     * @return 이메일 (null이 아님)
     */
    @Contract("->!null")
    public abstract String getEmail();

    /**
     * 인증 정보에서 이름을 반환합니다.
     * @return 이름 (null이 아님)
     */
    @Contract("->!null")
    public abstract String getName();

    /**
     * 최종 사용자(리소스 소유자)의 사용자 속성을 반환합니다.
     * @return 사용자 속성 (null이 아님)
     */
    @Contract("->!null")
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    /**
     * 프로바이더에게 받은 인증 정보를 반환합니다.
     * @param registrationId 프로바이더 ID
     * @param attributes 인증 정보
     * @return 인증 정보
     */
    public static CustomOAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        if (CustomOAuth2Provider.KAKAO.equalsWith(registrationId)) {
            return new KakaoOAuth2UserInfo(attributes);
        } else if (CustomOAuth2Provider.NAVER.equalsWith(registrationId)) {
            return new NaverOAuth2UserInfo(attributes);
        } else {
            throw new IllegalArgumentException(String.format("프로바이더 [%s]는 지원하지 않습니다.", registrationId));
        }
    }
}
