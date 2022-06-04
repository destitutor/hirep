package net.hexabrain.hireo.utils;

public class HangulUtils {
    private HangulUtils() { }

    public static boolean isHangul(char c) {
        return (c >= 0xAC00 && c <= 0xD7A3);
    }

    public static boolean isChosung(char c) {
        return (c >= 0x3131 && c <= 0x314E);
    }

    public static boolean isJungsung(char c) {
        return (c >= 0x314F && c <= 0x3163);
    }

    public static boolean isJongsung(char c) {
        return (c >= 0x3164 && c <= 0x3182);
    }
}
