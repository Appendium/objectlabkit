package net.objectlab.kit.util;

public final class BooleanUtil {
    private BooleanUtil() {
    }

    public static boolean isTrueOrNull(final Boolean b) {
        return b == null ? true : b;
    }

    public static boolean isFalseOrNull(final Boolean b) {
        return b == null ? true : !b;
    }

    public static boolean isTrue(final Boolean b) {
        return b == null ? false : b;
    }

    /**
     * @return true if string is Y,y,YES,yes,TRUE,true,T,t
     */
    public static boolean isTrue(final String str) {
        return str == null ? false : StringUtil.equalsAnyIgnoreCase(str, "yes", "y", "TRUE", "t");
    }

    public static boolean isFalse(final Boolean b) {
        return b == null ? true : !b;
    }
}
