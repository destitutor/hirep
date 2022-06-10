package net.hexabrain.hireo.utils;

/**
 * 이 클래스는 한글 여부를 검사하는 유틸리티 클래스이다.
 * 지금은 사용되지 않는 오래된 한글 문자는 지원하지 않는다.
 * @author Dohyeon Kim
 */
public class HangulUtils {
    /* 호환용 한글 자모(Hangul Compatibility Jamo) 유니코드 코드 값 */
    private static final int LAST_CONSONANT_START = 0x3131;
    private static final int LAST_CONSONANT_END = 0x314E;
    private static final int MIDDLE_VOWEL_START = 0x314F;
    private static final int MIDDLE_VOWEL_END = 0x3163;

    /* 한글 소리 마디(Hangul Syllables) 유니코드 코드 값 */
    private static final int COMPLETE_TYPE_START = 0xAC00;
    private static final int COMPLETE_TYPE_END = 0xD7A3;

    /* 초중종성 순서 */
    private static final char[] FIRST_CONSONANTS = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private static final char[] MIDDLE_VOWELS = {
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    };
    private static final char[] LAST_CONSONANTS = {
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private static final char[] DOUBLE_CONSONANTS = {
            'ㄲ', 'ㄸ', 'ㅃ', 'ㅆ', 'ㅉ'
    };
    private static final char[] DOUBLE_FINAL_CONSONANTS = {
            'ㄳ', 'ㄵ', 'ㄶ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅄ'
    };
    /* 문자를 초성으로 매핑하기 위한 배열. 겹받침(ㄲ, ㄸ 등) 때문에 제대로 인덱스를 찾지 못하는 경우를 방지한다. */
    private static final int[] FIRST_CONSONANT_MAP = {
            0, 1, -1, 2, -1, -1, 3, 4, 5, -1, -1, -1, -1, -1, -1, -1, 6, 7, 8, -1, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18
    };

    private HangulUtils() {
        throw new IllegalStateException("인스턴스화 할 수 없습니다.");
    }

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
    public static boolean isFirstConsonant(char c) {
        return isLastConsonant(c) && !isDoubleFinalConsonant(c);
    }

    /**
     * 입력된 문자가 중성인지 확인한다.
     * @param c 문자
     * @return 중성이면 true, 아니면 false
     */
    public static boolean isMiddleVowel(char c) {
        return (c >= MIDDLE_VOWEL_START && c <= MIDDLE_VOWEL_END);
    }

    /**
     * 입력된 문자가 종성인지 확인한다.
     * @param c 문자
     * @return 종성이면 true, 아니면 false
     */
    public static boolean isLastConsonant(char c) {
        return (c >= LAST_CONSONANT_START && c <= LAST_CONSONANT_END);
    }

    /**
     * 입력된 문자가 쌍자음인지 확인한다.
     * @param c 문자
     * @return 쌍자음이면 true, 아니면 false
     */
    public static boolean isDoubleConsonant(char c) {
        for (char doubleConsonant : DOUBLE_CONSONANTS) {
            if (c == doubleConsonant) {
                return true;
            }
        }
        return false;
    }

    /** 입력된 문자가 겹받침인지 확인한다.
     * @param c 문자
     * @return 겹받침이면 true, 아니면 false
     */
    public static boolean isDoubleFinalConsonant(char c) {
        for (int i = DOUBLE_FINAL_CONSONANTS.length - 1; i >= 0; i--) {
            if (DOUBLE_FINAL_CONSONANTS[i] == c) {
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
        return isMiddleVowel(c) || isLastConsonant(c) || isCompleteType(c);
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
    public static char getFirstConsonant(char c) {
        if (!isFirstConsonant(c) && !isCompleteType(c)) {
            throw new IllegalArgumentException("입력된 문자에 초성이 없습니다.");
        }

        if (isFirstConsonant(c)) {
            return c;
        } else {
            return FIRST_CONSONANTS[((c - COMPLETE_TYPE_START) / LAST_CONSONANTS.length) / MIDDLE_VOWELS.length];
        }
    }

    /**
     * 입력된 문자에서 중성을 추출한다.
     * @param c 문자
     * @return 중성
     * @throws IllegalArgumentException 문자가 중성 혹은 완성형 문자가 아닐 경우
     */
    public static char getMiddleVowel(char c) {
        if (!isMiddleVowel(c) && !isCompleteType(c)) {
            throw new IllegalArgumentException("입력된 문자에서 중성이 없습니다.");
        }

        if (isMiddleVowel(c)) {
            return c;
        } else {
            return MIDDLE_VOWELS[((c - COMPLETE_TYPE_START) / LAST_CONSONANTS.length) % MIDDLE_VOWELS.length];
        }
    }

    /**
     * 입력된 문자에서 종성을 추출한다.
     * @param c 문자
     * @return 종성
     * @throws IllegalArgumentException 문자가 종성 혹은 완성형 문자가 아닐 경우
     */
    public static char getLastConsonant(char c) {
        if (!isLastConsonant(c) && !isCompleteType(c)) {
            throw new IllegalArgumentException("입력된 문자에 종성을 가져올 수 없습니다.");
        }

        if (isLastConsonant(c)) {
            return c;
        } else {
            return LAST_CONSONANTS[(c - COMPLETE_TYPE_START) % LAST_CONSONANTS.length];
        }
    }

    /**
     * 입력된 초성과 중성, 종성을 조합하여 한글 문자를 생성한다.
     * @param firstIndex 초성 인덱스
     * @param middleIndex 중성 인덱스
     * @param lastIndex 종성 인덱스
     * @return 한글 문자
     */
    private static char combine(int firstIndex, int middleIndex, int lastIndex) {
        return (char)(((firstIndex * MIDDLE_VOWELS.length) + middleIndex)
                * LAST_CONSONANTS.length + lastIndex + COMPLETE_TYPE_START);
    }

    /**
     * 입력된 초성에서 완성형 시작 문자를 가져온다.
     * 예를 들어서, 입력된 초성이 'ㄱ'이면 '가'를, 'ㄴ'이면 '나'를, 'ㄷ'이면 '다'를 반환한다.
     * @param firstConsonant 초성
     * @return 완성형 시작 문자
     * @throws IllegalArgumentException 입력된 문자가 초성이 아닐 경우
     */
    public static char getCompleteStartLetterFrom(char firstConsonant) {
        if (!isFirstConsonant(firstConsonant)) {
            throw new IllegalArgumentException("입력된 문자가 초성이 아닙니다.");
        }

        int firstConsonantIndex = FIRST_CONSONANT_MAP[firstConsonant - LAST_CONSONANT_START];
        int middleVowelIndex = 0;
        int lastConsonantIndex = 0;
        return combine(firstConsonantIndex, middleVowelIndex, lastConsonantIndex);
    }

    /**
     * 입력된 초성에서 완성형 마지막 문자를 가져온다.
     * @param firstConsonant 초성
     * @return 완성형 마지막 문자
     */
    public static char getCompleteEndLetterFrom(char firstConsonant) {
        if (!isFirstConsonant(firstConsonant)) {
            throw new IllegalArgumentException("입력된 문자가 초성이 아닙니다.");
        }

        int firstConsonantIndex = FIRST_CONSONANT_MAP[firstConsonant - LAST_CONSONANT_START];
        int middleVowelIndex = MIDDLE_VOWELS.length - 1;
        int lastConsonantIndex = LAST_CONSONANTS.length - 1;
        return combine(firstConsonantIndex, middleVowelIndex, lastConsonantIndex);
    }
}