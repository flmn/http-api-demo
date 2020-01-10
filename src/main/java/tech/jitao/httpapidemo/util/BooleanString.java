package tech.jitao.httpapidemo.util;

public final class BooleanString {
    public static final String YES = "Y";
    public static final String NO = "N";

    public static boolean toBool(String str) {
        return YES.equals(str);
    }

    public static String toString(Boolean bool) {
        if (bool != null && bool) {
            return YES;
        } else {
            return NO;
        }
    }
}
