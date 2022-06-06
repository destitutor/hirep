package net.hexabrain.hireo.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class HangulUtilsTest {

    /* == 초중종성 여부 검사 테스트 == */
    @ParameterizedTest
    @MethodSource({"provideChosungs", "provideJungsungs", "provideDoubleFinalConsonants", "provideCompleteHangul"})
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
    @MethodSource("provideChosungs")
    @DisplayName("초성인 경우 참을 반환함")
    void isChosung(final char input) {
        assertThat(HangulUtils.isChosung(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({ "provideDoubleFinalConsonants", "provideJungsungs", "provideCompleteHangul",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("초성이 아닌 경우 거짓을 반환함")
    void isNotChosung(final char input) {
        assertThat(HangulUtils.isChosung(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideJungsungs")
    @DisplayName("중성인 경우 참을 반환함")
    void isJungsung(final char input) {
        assertThat(HangulUtils.isJungsung(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({ "provideDoubleFinalConsonants", "provideChosungs", "provideCompleteHangul",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("중성이 아닌 경우 거짓을 반환함")
    void isNotJungsung(final char input) {
        assertThat(HangulUtils.isJungsung(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource({"provideDoubleFinalConsonants", "provideChosungs"})
    @DisplayName("종성인 경우 참을 반환함")
    void isJongsung(final char input) {
        assertThat(HangulUtils.isJongsung(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({ "provideJungsungs", "provideCompleteHangul",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("종성이 아닌 경우 거짓을 반환함")
    void isNotJongsung(final char input) {
        assertThat(HangulUtils.isJongsung(input)).isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideDoubleFinalConsonants")
    @DisplayName("받침인 경우 참을 반환함")
    void isDoubleFinalConsonant(final char input) {
        assertThat(HangulUtils.isDoubleFinalConsonant(input)).isTrue();
    }

    @ParameterizedTest
    @MethodSource({ "provideChosungs", "provideJungsungs", "provideCompleteHangul",
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
    @MethodSource({ "provideChosungs", "provideJungsungs", "provideDoubleFinalConsonants",
                    "provideAlphabet", "provideNumbers", "provideSpecialChars" } )
    @DisplayName("완성형 한글이 아닌 경우 거짓을 반환함")
    void isNotCompleteHangul(final char input) {
        assertThat(HangulUtils.isCompleteType(input)).isFalse();
    }

    /* == 초중종성 추출 테스트 == */
    @ParameterizedTest
    @CsvSource({"가,ㄱ", "나,ㄴ", "다,ㄷ", "라,ㄹ", "빠,ㅃ", "싸,ㅆ", "짜,ㅉ", "깎,ㄲ", "낚,ㄴ", "닦,ㄷ"})
    @DisplayName("초성 추출 검사")
    void extractChosung(final char input, final char expected) {
        assertThat(HangulUtils.getChosung(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"가,ㅏ", "냐,ㅑ", "디,ㅣ", "로,ㅗ", "류,ㅠ", "쓖,ㅠ", "삡,ㅣ", "뢔,ㅙ"})
    @DisplayName("중성 추출 검사")
    void extractJungsung(final char input, final char expected) {
        assertThat(HangulUtils.getJungsung(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({"가,' '", "각,ㄱ", "낙,ㄱ", "달,ㄹ", "맘,ㅁ", "빫,ㄼ", "쌂,ㄻ"})
    @DisplayName("종성 추출 검사")
    void extractJongsung(final char input, final char expected) {
        assertThat(HangulUtils.getJongsung(input)).isEqualTo(expected);
    }

    /* == 테스트 픽스처 == */
    private static char[] provideChosungs() {
        return new char[]{'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'};
    }

    private static char[] provideJungsungs() {
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