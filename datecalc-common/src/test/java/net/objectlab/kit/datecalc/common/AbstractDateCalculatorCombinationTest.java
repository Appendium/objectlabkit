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

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import junit.framework.Assert;

public abstract class AbstractDateCalculatorCombinationTest<E extends Serializable> extends AbstractDateTestCase<E> {

    public void testInvalidCombinationDiffHandler() {
        final DateCalculator<E> cal1 = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        final DateCalculator<E> cal2 = newDateCalculator("bla2", HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNullNotNullHandler() {
        final DateCalculator<E> cal1 = newDateCalculator("bla", null);
        final DateCalculator<E> cal2 = newDateCalculator("bla2", HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNotNullNullHandler() {
        final DateCalculator<E> cal1 = newDateCalculator("bla", HolidayHandlerType.FORWARD);
        final DateCalculator<E> cal2 = newDateCalculator("bla2", null);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testValidCombinationOneEmptySet() {
        registerHolidays("UK", createUKHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("bla", HolidayHandlerType.FORWARD);

        // we MUST provide a set with boundaries.
        final Set<E> emptySet = Collections.emptySet();
        final HolidayCalendar<E> hol = new DefaultHolidayCalendar<E>(emptySet, newDate("2006-01-01"), newDate("2020-12-31"));
        cal1.setHolidayCalendar(hol);

        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final DateCalculator<E> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "bla/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getHolidayCalendar().getHolidays().size());
    }

    public void testValidCombination() {
        registerHolidays("UK", createUKHolidayCalendar());
        registerHolidays("UK", createUKHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("UK", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final DateCalculator<E> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "UK/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getHolidayCalendar().getHolidays().size());
        Assert.assertEquals("Early Boundary", newDate("2006-01-01"), combo.getHolidayCalendar().getEarlyBoundary());
        Assert.assertEquals("Late Boundary", newDate("2020-12-31"), combo.getHolidayCalendar().getLateBoundary());
    }

    public void testValidCombination2Sets() {
        registerHolidays("UK", createUKHolidayCalendar());
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final DateCalculator<E> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "US/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 6, combo.getHolidayCalendar().getHolidays().size());
        Assert.assertEquals("Early Boundary", newDate("2006-01-01"), combo.getHolidayCalendar().getEarlyBoundary());
        Assert.assertEquals("Late Boundary", newDate("2020-12-31"), combo.getHolidayCalendar().getLateBoundary());
    }

    public void testNullCombination() {
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<E> combo = cal1.combine(null);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getHolidayCalendar().getHolidays().size());
    }

    public void testSameCombination() {
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<E> combo = cal1.combine(cal1);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getHolidayCalendar().getHolidays().size());
        Assert.assertEquals("Early Boundary", newDate("2005-01-01"), combo.getHolidayCalendar().getEarlyBoundary());
        Assert.assertEquals("Late Boundary", newDate("2021-12-31"), combo.getHolidayCalendar().getLateBoundary());
    }

    public void testInvalidEarlyBoundary() {
        checkInvalidEarlyBoundary(HolidayHandlerType.FORWARD);
        checkInvalidEarlyBoundary(HolidayHandlerType.BACKWARD);
        checkInvalidEarlyBoundary(HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK);
        checkInvalidEarlyBoundary(HolidayHandlerType.MODIFIED_FOLLOWING);
        checkInvalidEarlyBoundary(HolidayHandlerType.MODIFIED_PRECEDING);
    }

    public void checkInvalidEarlyBoundary(final String type) {
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", type);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<E> cal2 = newDateCalculator("BLA", type);
        cal1.setStartDate(localDate);

        try {
            cal2.combine(cal1);
            fail("Combination should have thrown an exception because of boundary");
        } catch (final IllegalArgumentException e) {
            // all ok
        }

        try {
            cal1.combine(cal2);
            fail("Combination should have thrown an exception because of boundary");
        } catch (final IllegalArgumentException e) {
            // all ok
        }
    }

    public void testInvalidLateBoundary() {
        checkInvalidLateBoundary(HolidayHandlerType.FORWARD);
        checkInvalidLateBoundary(HolidayHandlerType.BACKWARD);
        checkInvalidLateBoundary(HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK);
        checkInvalidLateBoundary(HolidayHandlerType.MODIFIED_FOLLOWING);
        checkInvalidLateBoundary(HolidayHandlerType.MODIFIED_PRECEDING);
    }

    public void checkInvalidLateBoundary(final String type) {
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", type);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<E> cal2 = newDateCalculator("BLA", type);
        // we MUST provide a set with boundaries.
        final Set<E> emptySet = Collections.emptySet();
        final HolidayCalendar<E> hol = new DefaultHolidayCalendar<E>(emptySet, newDate("2006-01-01"), null);
        cal2.setHolidayCalendar(hol);
        cal2.setStartDate(localDate);

        try {
            cal2.combine(cal1);
            fail("Combination should have thrown an exception because of boundary");
        } catch (final IllegalArgumentException e) {
            // all ok
        }

        try {
            cal1.combine(cal2);
            fail("Combination should have thrown an exception because of boundary");
        } catch (final IllegalArgumentException e) {
            // all ok
        }
    }

    public void testInvalidExplicitEarlyBoundary() {
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<E> cal2 = newDateCalculator("BLA", HolidayHandlerType.FORWARD);
        // we MUST provide a set with boundaries.
        final Set<E> emptySet = Collections.emptySet();
        final HolidayCalendar<E> hol = new DefaultHolidayCalendar<E>(emptySet, null, newDate("2006-12-31"));
        cal2.setHolidayCalendar(hol);
        cal2.setStartDate(localDate);

        try {
            cal2.combine(cal1);
            fail("Combination should have thrown an exception because of boundary");
        } catch (final IllegalArgumentException e) {
            // all ok
        }

        try {
            cal1.combine(cal2);
            fail("Combination should have thrown an exception because of boundary");
        } catch (final IllegalArgumentException e) {
            // all ok
        }
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
