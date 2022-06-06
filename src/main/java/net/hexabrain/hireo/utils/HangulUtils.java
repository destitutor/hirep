package net.hexabrain.hireo.utils;

/**
 * 이 클래스는 한글 여부를 검사하는 유틸리티 클래스이다.
 * 지금은 사용되지 않는 오래된 한글 문자는 지원하지 않는다.
 * @author Dohyeon Kim
 */
public class HangulUtils {
    /* 호환용 한글 자모(Hangul Compatibility Jamo) 유니코드 코드 값 */
    private static final int JONGSUNG_START = 0x3131;
    private static final int JONGSUNG_END = 0x314E;
    private static final int JUNGSUNG_START = 0x314F;
    private static final int JUNGSUNG_END = 0x3163;

    /* 한글 소리 마디(Hangul Syllables) 유니코드 코드 값 */
    private static final int COMPLETE_TYPE_START = 0xAC00;
    private static final int COMPLETE_TYPE_END = 0xD7A3;

    /* 초중종성 순서 */
    private static final char[] CHOSUNG = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private static final char[] JUNGSUNG = {
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    };
    private static final char[] JONGSUNG = {
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private static final char[] DOUBLE_FINAL_CONSONANT = {
            'ㄳ', 'ㄵ', 'ㄶ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅄ'
    };

    private HangulUtils() { }

    /**
     * 입력된 문자가 완성형 문자인지 확인한다.
     * @param c 문자
     * @return 완성형 문자이면 true, 아니면 false
     */
    public static boolean isCompleteType(char c) {
        return (c >= COMPLETE_TYPE_START && c <= COMPLETE_TYPE_END);
    }

    /**
     * 입력된 문자가 초성인지 확인한다.
     * @param c 문자
     * @return 초성이면 true, 아니면 false
     */
    public static boolean isChosung(char c) {
        return isJongsung(c) && !isDoubleFinalConsonant(c);
    }

    /**
     * 입력된 문자가 중성인지 확인한다.
     * @param c 문자
     * @return 중성이면 true, 아니면 false
     */
    public static boolean isJungsung(char c) {
        return (c >= JUNGSUNG_START && c <= JUNGSUNG_END);
    }

    /**
     * 입력된 문자가 종성인지 확인한다.
     * @param c 문자
     * @return 종성이면 true, 아니면 false
     */
    public static boolean isJongsung(char c) {
        return (c >= JONGSUNG_START && c <= JONGSUNG_END);
    }

    /** 입력된 문자가 겹받침인지 확인한다.
     * @param c 문자
     * @return 겹받침이면 true, 아니면 false
     */
    public static boolean isDoubleFinalConsonant(char c) {
        for (int i = DOUBLE_FINAL_CONSONANT.length - 1; i >= 0; i--) {
            if (DOUBLE_FINAL_CONSONANT[i] == c) {
                return true;
            }
        }
        return false;
    }

    /**
     * 입력된 문자가 한글인지 확인한다.
     * @param c 문자
     * @return 한글이면 true, 아니면 false
     */
    public static boolean isHangul(char c) {
        return isJungsung(c) || isJongsung(c) || isCompleteType(c);
    }

    /**
     * 입력된 문자열이 한글인지 확인한다.
     * @param str 문자열
     * @return 한글이면 true, 아니면 false
     */
    public static boolean isHangul(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!isHangul(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 입력된 문자에서 초성을 추출한다.
     * @param c 문자
     * @return 초성
     * @throws IllegalArgumentException 문자가 초성 혹은 완성형 문자가 아닐 경우
     */
    public static char getChosung(char c) {
        if (!isChosung(c) && !isCompleteType(c)) {
            throw new IllegalArgumentException("입력된 문자에 초성이 없습니다.");
        }

        if (isChosung(c)) {
            return c;
        } else {
            return CHOSUNG[((c - COMPLETE_TYPE_START) / JONGSUNG.length) / JUNGSUNG.length];
        }
    }

    /**
     * 입력된 문자에서 중성을 추출한다.
     * @param c 문자
     * @return 중성
     * @throws IllegalArgumentException 문자가 중성 혹은 완성형 문자가 아닐 경우
     */
    public static char getJungsung(char c) {
        if (!isJungsung(c) && !isCompleteType(c)) {
            throw new IllegalArgumentException("입력된 문자에서 중성이 없습니다.");
        }

        if (isJungsung(c)) {
            return c;
        } else {
            return JUNGSUNG[((c - COMPLETE_TYPE_START) / JONGSUNG.length) % JUNGSUNG.length];
        }
    }

    /**
     * 입력된 문자에서 종성을 추출한다.
     * @param c 문자
     * @return 종성
     * @throws IllegalArgumentException 문자가 종성 혹은 완성형 문자가 아닐 경우
     */
    public static char getJongsung(char c) {
        if (!isJongsung(c) && !isCompleteType(c)) {
            throw new IllegalArgumentException("입력된 문자에 종성을 가져올 수 없습니다.");
        }

        if (isJongsung(c)) {
            return c;
        } else {
            return JONGSUNG[(c - COMPLETE_TYPE_START) % JONGSUNG.length];
        }
    }
}