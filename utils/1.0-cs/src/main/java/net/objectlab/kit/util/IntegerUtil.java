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
