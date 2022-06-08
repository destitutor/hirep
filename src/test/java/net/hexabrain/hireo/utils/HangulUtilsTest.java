package net.hexabrain.hireo.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThat;

class HangulUtilsTest {

    /* == 초중종성 여부 검사 테스트 == */
    @ParameterizedTest
    @MethodSource({"provideFirstConsonants", "provideMiddleVowels", "provideDoubleFinalConsonants", "provideCompleteHangul"})
    @DisplayName("한글인 경우 참을 반환함")
    void isHangul(final char input) {
        assertThat(HangulUtils.isHangul(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({"provideAlphabet", "provideNumbers", "provideSpecialChars"} )
    @DisplayName("한글이 아닌 경우 거짓을 반환함")
    void isNotHangul(final char input) {
        assertThat(HangulUtils.isHangul(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideFirstConsonants")
    @DisplayName("초성인 경우 참을 반환함")
    void isFirstConsonant(final char input) {
        assertThat(HangulUtils.isFirstConsonant(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({ "provideDoubleFinalConsonants", "provideMiddleVowels", "provideCompleteHangul",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("초성이 아닌 경우 거짓을 반환함")
    void isNotFirstConsonant(final char input) {
        assertThat(HangulUtils.isFirstConsonant(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideMiddleVowels")
    @DisplayName("중성인 경우 참을 반환함")
    void isMiddleVowel(final char input) {
        assertThat(HangulUtils.isMiddleVowel(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({ "provideDoubleFinalConsonants", "provideFirstConsonants", "provideCompleteHangul",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("중성이 아닌 경우 거짓을 반환함")
    void isNotMiddleVowel(final char input) {
        assertThat(HangulUtils.isMiddleVowel(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource({"provideDoubleFinalConsonants", "provideFirstConsonants"})
    @DisplayName("종성인 경우 참을 반환함")
    void isLastConsonant(final char input) {
        assertThat(HangulUtils.isLastConsonant(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({"provideMiddleVowels", "provideCompleteHangul",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("종성이 아닌 경우 거짓을 반환함")
    void isNotLastConsonant(final char input) {
        assertThat(HangulUtils.isLastConsonant(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideDoubleFinalConsonants")
    @DisplayName("받침인 경우 참을 반환함")
    void isDoubleFinalConsonant(final char input) {
        assertThat(HangulUtils.isDoubleFinalConsonant(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({"provideFirstConsonants", "provideMiddleVowels", "provideCompleteHangul",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("받침이 아닌 경우 거짓을 반환함")
    void isNotDoubleFinalConsonant(final char input) {
        assertThat(HangulUtils.isDoubleFinalConsonant(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideCompleteHangul")
    @DisplayName("완성형 한글인 경우 참을 반환함")
    void isCompleteHangul(final char input) {
        assertThat(HangulUtils.isCompleteType(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({"provideFirstConsonants", "provideMiddleVowels", "provideDoubleFinalConsonants",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("완성형 한글이 아닌 경우 거짓을 반환함")
    void isNotCompleteHangul(final char input) {
        assertThat(HangulUtils.isCompleteType(input)).isFalse();
    }

    /* == 초중종성 추출 테스트 == */
    @ParameterizedTest
    @CsvSource({"가,ㄱ", "나,ㄴ", "다,ㄷ", "라,ㄹ", "빠,ㅃ", "싸,ㅆ", "짜,ㅉ", "깎,ㄲ", "낚,ㄴ", "닦,ㄷ"})
    @DisplayName("초성 추출 검사")
    void extractFirstConsonant(final char input, final char expected) {
        assertThat(HangulUtils.getFirstConsonant(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"가,ㅏ", "냐,ㅑ", "디,ㅣ", "로,ㅗ", "류,ㅠ", "쓖,ㅠ", "삡,ㅣ", "뢔,ㅙ"})
    @DisplayName("중성 추출 검사")
    void extractMiddleVowel(final char input, final char expected) {
        assertThat(HangulUtils.getMiddleVowel(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"가,' '", "각,ㄱ", "낙,ㄱ", "달,ㄹ", "맘,ㅁ", "빫,ㄼ", "쌂,ㄻ"})
    @DisplayName("종성 추출 검사")
    void extractLastConsonant(final char input, final char expected) {
        assertThat(HangulUtils.getLastConsonant(input)).isEqualTo(expected);
    }

    /* == 테스트 픽스처 == */
    private static char[] provideFirstConsonants() {
        return new char[]{'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
    }

    private static char[] provideMiddleVowels() {
        return new char[]{'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'};
    }

    private static char[] provideDoubleFinalConsonants() {
        return new char[]{'ㄳ', 'ㄵ', 'ㄶ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅄ'};
    }

    private static char[] provideAlphabet() {
        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    }

    private static char[] provideNumbers() {
        return new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    }

    private static char[] provideSpecialChars() {
        return new char[]{'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=', '~', '`', '|', '\\', ';', ':', '\'', '\"', ',', '.', '<', '>', '/', '?'};
    }

    private static char[] provideCompleteHangul() {
        return new char[]{'가', '나', '다', '라', '마', '사', '아', '자', '차', '카', '타', '파', '하'};
    }
}