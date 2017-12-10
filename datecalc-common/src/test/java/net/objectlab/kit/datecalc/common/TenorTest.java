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
 * $Id$
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
package net.objectlab.kit.datecalc.common;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TenorTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName
     *            name of the test case
     */
    public TenorTest(final String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TenorTest.class);
    }

    public void testValidTenors() {
        checkValidTenor("SP", false, TenorCode.SPOT, 0);
        checkValidTenor("ON", false, TenorCode.OVERNIGHT, 0);

        checkValidTenor("1D", true, TenorCode.DAY, 1);
        checkValidTenor("0D", true, TenorCode.DAY, 0);
        checkValidTenor("234D", true, TenorCode.DAY, 234);
        // checkValidTenor("-1D", true, TenorCode.DAY, -1);

        checkValidTenor("1M", true, TenorCode.MONTH, 1);
        checkValidTenor("0M", true, TenorCode.MONTH, 0);
        checkValidTenor("234M", true, TenorCode.MONTH, 234);
        // checkValidTenor("-1M", true, TenorCode.MONTH, -1);

        checkValidTenor("1W", true, TenorCode.WEEK, 1);
        checkValidTenor("0W", true, TenorCode.WEEK, 0);
        checkValidTenor("234W", true, TenorCode.WEEK, 234);
        // checkValidTenor("-1W", true, TenorCode.WEEK, -1);

        checkValidTenor("1Y", true, TenorCode.YEAR, 1);
        checkValidTenor("0Y", true, TenorCode.YEAR, 0);
        checkValidTenor("234Y", true, TenorCode.YEAR, 234);
        // checkValidTenor("-1Y", true, TenorCode.YEAR, -1);
    }

    public void testToString() {
        checkToString("SP");
        checkToString("ON");
        checkToString("1D");
        checkToString("1W");
        checkToString("1M");
        checkToString("2M");
        checkToString("234D");
        checkToString("1Y");
    }

    public void testHashcode() {
        final Set<Tenor> set = new HashSet<Tenor>();
        set.add(StandardTenor.OVERNIGHT);
        set.add(StandardTenor.T_1D);
        set.add(StandardTenor.T_15Y);
        assertEquals("size", 3, set.size());
        assertTrue("Contains " + StandardTenor.T_1D, set.contains(StandardTenor.T_1D));
        assertTrue("Contains " + StandardTenor.OVERNIGHT, set.contains(StandardTenor.OVERNIGHT));
        assertTrue("Contains " + StandardTenor.T_15Y, set.contains(StandardTenor.T_15Y));
        assertEquals("Equals ", StandardTenor.T_15Y, StandardTenor.T_15Y);
    }

    public void testEquals() {
        assertEquals("Equals ", StandardTenor.T_15Y, StandardTenor.T_15Y);
        assertTrue("same", StandardTenor.OVERNIGHT.equals(StandardTenor.OVERNIGHT));
        assertFalse("not same", StandardTenor.OVERNIGHT.equals(StandardTenor.SPOT));
        assertFalse("Different class", StandardTenor.OVERNIGHT.equals("Hello"));
        assertFalse("Null", StandardTenor.OVERNIGHT.equals(null));
        assertTrue("same", StandardTenor.T_10Y.equals(StandardTenor.T_10Y));
        assertFalse("diff unit", StandardTenor.T_10Y.equals(StandardTenor.T_15Y));
        assertTrue("same", StandardTenor.T_10Y.equals(new Tenor(10, TenorCode.YEAR)));
        assertFalse("same", new Tenor(10, null).equals(new Tenor(10, TenorCode.YEAR)));
        assertTrue("same", new Tenor(10, null).equals(new Tenor(10, null)));
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testInvalidTenors() {
        checkInvalidTenor("GB");
        checkInvalidTenor("1SP");
        checkInvalidTenor("1IMM");
        checkInvalidTenor("IMMM");
        checkInvalidTenor("123SP");
        checkInvalidTenor("1SN");
        checkInvalidTenor("2SW");
        checkInvalidTenor("2SW55");
        checkInvalidTenor("3TN");
        checkInvalidTenor("3ON");
        checkInvalidTenor("D");
        checkInvalidTenor("M");
        checkInvalidTenor("W");
        checkInvalidTenor("Y");
    }

    private void checkToString(final String string) {
        try {
            final Tenor t = Tenor.valueOf(string);
            assertEquals(string, t.toString());
        } catch (final IllegalArgumentException e) {
            fail(e.toString());
        }
    }

    private void checkInvalidTenor(final String str) {
        try {
            Tenor.valueOf(str);
            fail(str + " is invalid and should have thrown an exception!");
        } catch (final IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    private Tenor checkValidTenor(final String str, final boolean acceptUnits, final TenorCode tenorCode, final int units) {
        Tenor tenor = null;
        try {
            tenor = Tenor.valueOf(str);
            assertEquals(str + " Accept Units", acceptUnits, tenor.getCode().acceptUnits());
            assertEquals(str + " Accept Units", acceptUnits, tenor.hasUnits());
            assertSame(str + " right tenor ", tenorCode, tenor.getCode());
            assertEquals(str + " Units", units, tenor.getUnits());
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
            fail("[" + str + "] should be valid!");
        }
        return tenor;
    }
}

/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.
 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 */
