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
import java.util.Set;

import junit.framework.Assert;

public abstract class AbstractDateCalculatorFactoryTest<E extends Serializable> extends AbstractDateTestCase<E> {

    public void testGetCalendarsNoHoliday() {
        final DateCalculator<E> cal1 = getDateCalculatorFactory().getDateCalculator("bla", null);
        Assert.assertNotNull("cal1", cal1);
        Assert.assertEquals("name", "bla", cal1.getName());
        Assert.assertTrue("no holiday", cal1.getHolidayCalendar().getHolidays().isEmpty());

        final DateCalculator<E> cal2 = getDateCalculatorFactory().getDateCalculator("bla", null);
        Assert.assertNotNull("cal2", cal2);
        Assert.assertEquals("name", "bla", cal2.getName());
        Assert.assertTrue("no holiday", cal2.getHolidayCalendar().getHolidays().isEmpty());
        Assert.assertNotSame(cal1, cal2);
    }

    public void testGetCalendarsNoHolidayButSomeRegistered() {
        getDateCalculatorFactory().registerHolidays("UK", createUKHolidayCalendar());

        final DateCalculator<E> cal1 = getDateCalculatorFactory().getDateCalculator("bla", null);
        Assert.assertNotNull("cal1", cal1);
        Assert.assertEquals("name", "bla", cal1.getName());
        Assert.assertTrue("no holiday", cal1.getHolidayCalendar().getHolidays().isEmpty());

        final DateCalculator<E> cal2 = getDateCalculatorFactory().getDateCalculator("UK", null);
        Assert.assertNotNull("cal2", cal2);
        Assert.assertEquals("name cal2", "UK", cal2.getName());
        Assert.assertEquals("UK holidays", 4, cal2.getHolidayCalendar().getHolidays().size());
        Assert.assertNotSame(cal1, cal2);
    }

    public void testGetCalendarsNames() {
        getDateCalculatorFactory().registerHolidays("UK", createUKHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("US", createUSHolidayCalendar());

        {
            final Set<String> cals = getDateCalculatorFactory().getRegisteredHolidayCalendarNames();
            Assert.assertNotNull("cal1", cals);
            Assert.assertEquals("cals size", 2, cals.size());
            Assert.assertTrue("UK", cals.contains("UK"));
            Assert.assertTrue("US", cals.contains("US"));
            Assert.assertTrue("BLA", !cals.contains("BLA"));
        }
        // unregister 1 name that does not exist
        getDateCalculatorFactory().unregisterHolidayCalendar("ZZZ");
        {
            final Set<String> cals = getDateCalculatorFactory().getRegisteredHolidayCalendarNames();
            Assert.assertNotNull("cal1", cals);
            Assert.assertEquals("cals size", 2, cals.size());
            Assert.assertTrue("UK", cals.contains("UK"));
            Assert.assertTrue("US", cals.contains("US"));
            Assert.assertTrue("BLA", !cals.contains("BLA"));
        }
        // unregister 1 name that does exist
        getDateCalculatorFactory().unregisterHolidayCalendar("UK");
        {
            final Set<String> cals = getDateCalculatorFactory().getRegisteredHolidayCalendarNames();
            Assert.assertNotNull("cal1", cals);
            Assert.assertEquals("cals size", 1, cals.size());
            Assert.assertTrue("UK", !cals.contains("UK"));
            Assert.assertTrue("US", cals.contains("US"));
            Assert.assertTrue("BLA", !cals.contains("BLA"));
        }
        // unregister 1 name that does exist
        getDateCalculatorFactory().unregisterHolidayCalendar("US");
        {
            final Set<String> cals = getDateCalculatorFactory().getRegisteredHolidayCalendarNames();
            Assert.assertNotNull("cal1", cals);
            Assert.assertEquals("cals size", 0, cals.size());
        }

        // re-register
        getDateCalculatorFactory().registerHolidays("UK", createUKHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("US", createUSHolidayCalendar());
        {
            final Set<String> cals = getDateCalculatorFactory().getRegisteredHolidayCalendarNames();
            Assert.assertNotNull("cal1", cals);
            Assert.assertEquals("cals size", 2, cals.size());
            Assert.assertTrue("UK", cals.contains("UK"));
            Assert.assertTrue("US", cals.contains("US"));
            Assert.assertTrue("BLA", !cals.contains("BLA"));
        }

        // unregister all
        getDateCalculatorFactory().unregisterAllHolidayCalendars();
        {
            final Set<String> cals = getDateCalculatorFactory().getRegisteredHolidayCalendarNames();
            Assert.assertNotNull("cal1", cals);
            Assert.assertEquals("cals size", 0, cals.size());
        }
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testGetCorrectAlgo() {
        DateCalculator<E> cal1 = getDateCalculatorFactory().getDateCalculator("bla", null);

        Assert.assertNull("No algo", cal1.getHolidayHandlerType());
        cal1 = getDateCalculatorFactory().getDateCalculator("bla", HolidayHandlerType.BACKWARD);
        Assert.assertEquals("Type", HolidayHandlerType.BACKWARD, cal1.getHolidayHandlerType());

        cal1 = getDateCalculatorFactory().getDateCalculator("bla", HolidayHandlerType.FORWARD);
        Assert.assertEquals("Type", HolidayHandlerType.FORWARD, cal1.getHolidayHandlerType());

        cal1 = getDateCalculatorFactory().getDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLOWING);
        Assert.assertEquals("Type", HolidayHandlerType.MODIFIED_FOLLOWING, cal1.getHolidayHandlerType());

        cal1 = getDateCalculatorFactory().getDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEDING);
        Assert.assertEquals("Type", HolidayHandlerType.MODIFIED_PRECEDING, cal1.getHolidayHandlerType());
    }

    public void testGetIncorrectAlgo() {
        try {
            getDateCalculatorFactory().getDateCalculator("bla", "bliobl");
            fail("Should have thrown UnsupportedOperationException");
        } catch (final IllegalArgumentException e) {
            // all ok
        }
    }

    public void testSetHolCal() {
        final DateCalculator<E> cal1 = getDateCalculatorFactory().getDateCalculator("bla", null);

        Assert.assertNotNull("No algo", cal1);
        Assert.assertNotNull("No hol", cal1.getHolidayCalendar().getHolidays());
        Assert.assertTrue("empty hol", cal1.getHolidayCalendar().getHolidays().isEmpty());

        cal1.setHolidayCalendar(null);
        Assert.assertNotNull("empty", cal1.getHolidayCalendar().getHolidays());
        Assert.assertTrue("empty hol", cal1.getHolidayCalendar().getHolidays().isEmpty());
    }

    public void testUseDefault() {
        final DateCalculator<E> cal1 = getDateCalculatorFactory().getDateCalculator("bla", null);

        Assert.assertNotNull("No algo", cal1);
        Assert.assertNotNull("No hol", cal1.getHolidayCalendar().getHolidays());
        Assert.assertTrue("empty hol", cal1.getHolidayCalendar().getHolidays().isEmpty());

        E date = cal1.moveByDays(0).getCurrentBusinessDate();
        Assert.assertEquals("default today", newDate(null), date);

        cal1.setStartDate(null);
        date = cal1.moveByDays(0).getCurrentBusinessDate();
        Assert.assertEquals("default today", newDate(null), date);

        cal1.setStartDate(newDate("2006-08-08"));
        cal1.setCurrentBusinessDate(null);
        date = cal1.moveByDays(0).getCurrentBusinessDate();
        Assert.assertEquals("default today", newDate(null), date);
    }

    public void testHolNoAlgo() {
        getDateCalculatorFactory().registerHolidays("UK", createUKHolidayCalendar());
        final DateCalculator<E> cal1 = getDateCalculatorFactory().getDateCalculator("UK", null);

        Assert.assertNotNull("No algo", cal1);
        Assert.assertNotNull("No hol", cal1.getHolidayCalendar().getHolidays());
        Assert.assertTrue("non empty hol", !cal1.getHolidayCalendar().getHolidays().isEmpty());

        cal1.setCurrentBusinessDate(newDate("2006-12-25"));
        Assert.assertTrue("current date is holiday", cal1.isCurrentDateNonWorking());

        cal1.setCurrentBusinessDate(newDate("2006-12-24"));
        Assert.assertTrue("current date is weekend", cal1.isCurrentDateNonWorking());
    }

    public void testHolidayCalendar() {
        final Set<E> uk = createUKHolidays();
        final HolidayCalendar<E> ukCal = new DefaultHolidayCalendar<E>(uk);
        getDateCalculatorFactory().registerHolidays("UK", ukCal);

        assertNull("Early boundary", ukCal.getEarlyBoundary());
        assertNull("Late boundary", ukCal.getLateBoundary());
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
