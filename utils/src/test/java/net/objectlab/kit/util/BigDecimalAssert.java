/**
 * 
 */
package net.objectlab.kit.util;

import java.math.BigDecimal;

import org.junit.Assert;

/**
 * @author Benoit
 *
 */
public class BigDecimalAssert {

    public static void assertSameValue(final String msg, final BigDecimal bd1, final BigDecimal bd2) {
        Assert.assertTrue(msg, BigDecimalUtil.isSameValue(bd1, bd2));
    }
}
