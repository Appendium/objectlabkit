package net.objectlab.kit.util;

/**
 * @author Benoit Xhenseval
 *
 */
public final class IntegerUtil {
    private IntegerUtil() {
    }

    /**
     * @return true if value !=null and <> 0.
     */
    public static boolean isNotZero(final Integer value) {
        return value != null && value.intValue() != 0;
    }

    /**
     * @return true if value !=null and 0.
     */
    public static boolean isZero(final Integer value) {
        return value != null && value.intValue() == 0;
    }

    /**
     * @return true if value ==null OR 0.
     */
    public static boolean isNullOrZero(final Integer value) {
        return value == null || value.intValue() == 0;
    }

    /**
     * @return true if val1 == val2 (ignoring scale)
     */
    public static boolean isSameValue(final Integer val1, final Integer val2) {
        return val1 == null && val2 == null || val1 != null && val2 != null && val1.compareTo(val2) == 0;
    }

    /**
     * @return true if val1 != val2 (ignoring scale)
     */
    public static boolean isNotSameValue(final Integer val1, final Integer val2) {
        return !isSameValue(val1, val2);
    }

    /**
     * Add 2 BigDecimal safely (i.e. handles nulls)
     */
    public static Integer safeAdd(final Integer v1, final Integer v2) {
        Integer total = v1;
        if (v1 != null && v2 != null) {
            total = v1 + v2;
        } else if (v2 != null) {
            total = v2;
        }
        return total;
    }

    public static int safeSignum(final Integer v) {
        if (v != null) {
            return v.intValue() > 0 ? 1 : v.intValue() < 0 ? -1 : 0;
        }
        return 0;
    }

    public static int safeCompare(final Integer id, final Integer id2) {
        int ret = -1;
        if (id != null && id2 != null) {
            ret = id.compareTo(id2);
        } else if (id == null && id2 == null) {
            ret = 0;
        } else if (id != null) {
            ret = 1;
        }
        return ret;
    }

    /**
     * Return the value unless it is null, in which case it returns the default value.
     * @param value
     * @param defaultValueIfNull
     * @return
     */
    public static Integer assign(final Integer value, final Integer defaultValueIfNull) {
        return value != null ? value : defaultValueIfNull;
    }

    public static boolean isNotZeroOrNegative(final Integer id) {
        return id != null && id.intValue() > 0;
    }
}
