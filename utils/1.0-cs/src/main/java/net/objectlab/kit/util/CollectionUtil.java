/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
 * $Id: AbstractDateCalculator.java 309 2010-03-23 21:01:49Z marchy $
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
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
            for (final Object item : items) {
                final boolean b = collection.contains(item);
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
        if (collections == null) {
            return false;
        }
        for (final Collection col : collections) {
            if (isEmpty(col)) {
                return false;
            }
        }
        return true;
    }

    public static boolean sameContent(final Collection c1, final Collection c2) {
        if (c1 == c2 || (isEmpty(c2) && isEmpty(c1))) {
            return true;
        }
        boolean same = false;
        if (c1 != null && c2 != null) {
            same = c1.size() == c2.size();
            if (same) {
                same = c1.equals(c2);
            }
        }
        return same;
    }
}
