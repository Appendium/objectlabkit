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
package net.objectlab.kit.datecalc.joda;

import java.util.Calendar;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class JodaWorkingWeekTest extends TestCase {

    private JodaWorkingWeek ww;

    @Override
    public void setUp() {
        ww = new JodaWorkingWeek();
    }

    public void testIsWorkingDayFromDateTimeConstant() {
        Assert.assertTrue("DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.MONDAY));
        Assert.assertTrue("DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.TUESDAY));
        Assert.assertTrue("DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.WEDNESDAY));
        Assert.assertTrue("DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.THURSDAY));
        Assert.assertTrue("DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.FRIDAY));
        Assert.assertFalse("DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertFalse("DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));
    }

    public void testIsWorkingDay() {
        LocalDate date = new LocalDate("2006-08-01");
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
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SUNDAY); // sunday
        // working day
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SUNDAY); // sunday
        // working day do it twice
        Assert.assertTrue("DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.MONDAY));
        Assert.assertTrue("DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.TUESDAY));
        Assert.assertTrue("DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.WEDNESDAY));
        Assert.assertTrue("DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.THURSDAY));
        Assert.assertTrue("DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.FRIDAY));
        Assert.assertFalse("DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertTrue("DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));

        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.SUNDAY); // sunday
        // working
        // day
        Assert.assertTrue("2/ DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.MONDAY));
        Assert.assertTrue("2/ DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.TUESDAY));
        Assert.assertTrue("2/ DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.WEDNESDAY));
        Assert.assertTrue("2/ DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.THURSDAY));
        Assert.assertTrue("2/ DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.FRIDAY));
        Assert.assertFalse("2/ DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertFalse("2/ DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));

        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SUNDAY); // sunday
        // working
        // day
        // do
        // it
        // twice
        Assert.assertFalse("2/ DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertTrue("2/ DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));
    }

    public void testJodaToCalendarDayConstant() {
        assertEquals("Monday", JodaWorkingWeek.jodaToCalendarDayConstant(DateTimeConstants.MONDAY), Calendar.MONDAY);
        assertEquals("Tuesday", JodaWorkingWeek.jodaToCalendarDayConstant(DateTimeConstants.TUESDAY), Calendar.TUESDAY);
        assertEquals("Wednesday", JodaWorkingWeek.jodaToCalendarDayConstant(DateTimeConstants.WEDNESDAY), Calendar.WEDNESDAY);
        assertEquals("Thursday", JodaWorkingWeek.jodaToCalendarDayConstant(DateTimeConstants.THURSDAY), Calendar.THURSDAY);
        assertEquals("Friday", JodaWorkingWeek.jodaToCalendarDayConstant(DateTimeConstants.FRIDAY), Calendar.FRIDAY);
        assertEquals("Saturday", JodaWorkingWeek.jodaToCalendarDayConstant(DateTimeConstants.SATURDAY), Calendar.SATURDAY);
        assertEquals("Sunday", JodaWorkingWeek.jodaToCalendarDayConstant(DateTimeConstants.SUNDAY), Calendar.SUNDAY);
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
