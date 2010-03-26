package net.objectlab.kit.util;

import java.math.BigDecimal;

public final class ObjectUtil {
    private ObjectUtil() {
    }

    /**
     * Return true if i1 equals (according to {@link Integer#equals(Object)}) 12.
     * Also returns true if 11 is null and 12 is null! Is safe on either 11 or 12 being null.
     */
    public static boolean equals(final Integer i1, final Integer i2) {
        if (i1 == null) {
            return i2 == null;
        }
        return i1.equals(i2);
    }

    /**
     * Return true if bd1 has the same value (according to
     * {@link BigDecimalUtil#isSameValue(BigDecimal, BigDecimal)}, which takes care of different scales)
     * as bd2.
     * Also returns true if bd1 is null and bd2 is null! Is safe on either bd1 or bd2 being null.
     */
    public static boolean equals(final BigDecimal bd1, final BigDecimal bd2) {
        if (bd1 == null) {
            return bd2 == null;
        }
        return BigDecimalUtil.isSameValue(bd1, bd2);
    }

    /**
     * Return true if o1 equals (according to {@link Object#equals(Object)}) o2.
     * Also returns true if o1 is null and o2 is null! Is safe on either o1 or o2 being null.
     */
    public static boolean equals(final Object o1, final Object o2) {
        if (o1 == null) {
            return o2 == null;
        }
        return o1.equals(o2);
    }

    /**
     * Return true if o1 equals (according to {@link Object#equals(Object)} any of the given objects.
     * Also returns true if o1 is null and any of the given objects is null as well!
     */
    public static boolean equalsAny(final Object o1, final Object... o2s) {
        for (final Object o2 : o2s) {
            if (o1 == null) {
                if (o2 == null) {
                    return true;
                }
                continue;
            }
            // o1 != null
            if (o1.equals(o2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if o1 equals (according to {@link Object#equals(Object)} ALL of the given objects.
     */
    public static boolean equalsAll(final Object o1, final Object... o2s) {
        for (final Object o2 : o2s) {
            if (o1 == null) {
                if (o2 != null) {
                    return false;
                }
                continue;
            }
            // o1 != null
            if (!o1.equals(o2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return true if o1 does NOT equal (according to {@link Object#equals(Object)} any of the given objects.
     * Also returns false if o1 is null and any of the given objects is null as well!
     */
    public static boolean notEqualsAny(final Object o1, final Object... o2s) {
        return !equalsAny(o1, o2s);
    }

    /**
     * Return true if any of the given objects are null.
     */
    public static boolean anyNull(final Object... o1s) {
        for (final Object o1 : o1s) {
            if (o1 == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if ALL of the given objects are null.
     */
    public static boolean allNull(final Object... o1s) {
        for (final Object o1 : o1s) {
            if (o1 != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return true if at least one of the given objects is not null.
     */
    public static boolean atLeastOneNotNull(final Object... o1s) {
        for (final Object o1 : o1s) {
            if (o1 != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return true if NONE of the given objects are null.
     */
    public static boolean noneNull(final Object... o1s) {
        return !anyNull(o1s);
    }
}
