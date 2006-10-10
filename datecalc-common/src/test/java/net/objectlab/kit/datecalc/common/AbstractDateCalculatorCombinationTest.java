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

import junit.framework.Assert;

public abstract class AbstractDateCalculatorCombinationTest<E> extends AbstractDateTestCase<E> {

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
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testValidCombinationOneEmptySet() {
        registerHolidays("UK", createUKHolidays());
        final DateCalculator<E> cal1 = newDateCalculator("bla", HolidayHandlerType.FORWARD);

        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final DateCalculator<E> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "bla/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination() {
        registerHolidays("UK", createUKHolidays());
        registerHolidays("UK", createUKHolidays());
        final DateCalculator<E> cal1 = newDateCalculator("UK", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final DateCalculator<E> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "UK/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination2Sets() {
        registerHolidays("UK", createUKHolidays());
        registerHolidays("US", createUSHolidays());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final DateCalculator<E> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "US/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 6, combo.getNonWorkingDays().size());
    }

    public void testNullCombination() {
        registerHolidays("US", createUSHolidays());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<E> combo = cal1.combine(null);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
    }

    public void testSameCombination() {
        registerHolidays("US", createUSHolidays());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<E> combo = cal1.combine(cal1);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
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
