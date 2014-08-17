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
package net.objectlab.kit.datecalc.jdk8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Calendar;

import junit.framework.Assert;
import junit.framework.TestCase;

public class Jdk8WorkingWeekTest extends TestCase {

    private Jdk8WorkingWeek ww;

    @Override
    public void setUp() {
        ww = new Jdk8WorkingWeek();
    }

    public void testIsWorkingDayFromDateTimeConstant() {
        Assert.assertTrue("DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.MONDAY));
        Assert.assertTrue("DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.TUESDAY));
        Assert.assertTrue("DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.WEDNESDAY));
        Assert.assertTrue("DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.THURSDAY));
        Assert.assertTrue("DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.FRIDAY));
        Assert.assertFalse("DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SATURDAY));
        Assert.assertFalse("DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SUNDAY));
    }

    public void testIsWorkingDay() {
        LocalDate date = LocalDate.parse("2006-08-01");
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDay(date));
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testSetWorkingDayFromDateTimeConstant() {
        ww = ww.withWorkingDayFromDateTimeConstant(true, DayOfWeek.SUNDAY); // sunday
        // working day
        ww = ww.withWorkingDayFromDateTimeConstant(true, DayOfWeek.SUNDAY); // sunday
        // working day do it twice
        Assert.assertTrue("DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.MONDAY));
        Assert.assertTrue("DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.TUESDAY));
        Assert.assertTrue("DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.WEDNESDAY));
        Assert.assertTrue("DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.THURSDAY));
        Assert.assertTrue("DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.FRIDAY));
        Assert.assertFalse("DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SATURDAY));
        Assert.assertTrue("DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SUNDAY));

        ww = ww.withWorkingDayFromDateTimeConstant(false, DayOfWeek.SUNDAY); // sunday
        // working
        // day
        Assert.assertTrue("2/ DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.MONDAY));
        Assert.assertTrue("2/ DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.TUESDAY));
        Assert.assertTrue("2/ DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.WEDNESDAY));
        Assert.assertTrue("2/ DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.THURSDAY));
        Assert.assertTrue("2/ DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.FRIDAY));
        Assert.assertFalse("2/ DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SATURDAY));
        Assert.assertFalse("2/ DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SUNDAY));

        ww = ww.withWorkingDayFromDateTimeConstant(true, DayOfWeek.SUNDAY); // sunday
        // working
        // day
        // do
        // it
        // twice
        Assert.assertFalse("2/ DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SATURDAY));
        Assert.assertTrue("2/ DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DayOfWeek.SUNDAY));
    }

    public void testJdk8ToCalendarDayConstant() {
        assertEquals("Monday", Jdk8WorkingWeek.jdk8ToCalendarDayConstant(DayOfWeek.MONDAY), Calendar.MONDAY);
        assertEquals("Tuesday", Jdk8WorkingWeek.jdk8ToCalendarDayConstant(DayOfWeek.TUESDAY), Calendar.TUESDAY);
        assertEquals("Wednesday", Jdk8WorkingWeek.jdk8ToCalendarDayConstant(DayOfWeek.WEDNESDAY), Calendar.WEDNESDAY);
        assertEquals("Thursday", Jdk8WorkingWeek.jdk8ToCalendarDayConstant(DayOfWeek.THURSDAY), Calendar.THURSDAY);
        assertEquals("Friday", Jdk8WorkingWeek.jdk8ToCalendarDayConstant(DayOfWeek.FRIDAY), Calendar.FRIDAY);
        assertEquals("Saturday", Jdk8WorkingWeek.jdk8ToCalendarDayConstant(DayOfWeek.SATURDAY), Calendar.SATURDAY);
        assertEquals("Sunday", Jdk8WorkingWeek.jdk8ToCalendarDayConstant(DayOfWeek.SUNDAY), Calendar.SUNDAY);
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
