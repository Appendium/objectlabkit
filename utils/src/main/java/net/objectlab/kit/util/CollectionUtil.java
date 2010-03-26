package net.objectlab.kit.util;

import java.util.Collection;

/**
 * @author Benoit Xhenseval
 *
 */
public final class CollectionUtil {
    private CollectionUtil() {
    }

    /**
     * @return true if collection null or empty.
     */
    public static boolean isEmpty(final Collection<?> col) {
        return col == null || col.isEmpty();
    }

    /**
     * @return true if collection not empty (null safe).
     */
    public static boolean isNotEmpty(final Collection<?> col) {
        return col != null && !col.isEmpty();
    }

    /**
     * @return true if collection has only 1 item (null safe).
     */
    public static boolean hasOneItem(final Collection<?> col) {
        return col != null && col.size() == 1;
    }

    /**
     * @return true if array not empty (null safe).
     */
    public static boolean isNotEmpty(final Object[] array) {
        return array != null && array.length > 0;
    }

    /**
     * @return size of collection if not null, otherwise 0.
     */
    public static int size(final Collection<?> col) {
        return col != null ? col.size() : 0;
    }

    /**
     * @return true if collection is not null and contains the items
     */
    public static boolean contains(final Collection<?> collection, final Object item) {
        return collection != null && collection.contains(item) ? true : false;
    }

    /**
     * @return true if collection is not null and contains the items
     */
    public static boolean containsAny(final Collection<?> collection, final Object... items) {
        if (collection != null) {
            for (Object item : items) {
                boolean b = collection.contains(item);
                if (b) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return true if none of the collections are empty or null
     */
    public static boolean noneEmpty(final Collection... collections) {
        for (final Collection col : collections) {
            if (isEmpty(col)) {
                return false;
            }
        }
        return true;
    }

    public static boolean sameContent(final Collection c1, final Collection c2) {
        boolean same = c1 != null && c2 != null || c1 == c2 || c1 == null && c2 == null;
        if (same && c1 != null) {
            same = c1.size() == c2.size();
            if (same) {
                same = c1.equals(c2);
            }
        }
        return same;
    }
}
