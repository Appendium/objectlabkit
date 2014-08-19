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

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

public class WorkingWeekTest extends TestCase {

    public void testIsWorkingDayFromCalendar() {
        final WorkingWeek ww = new WorkingWeek();
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));
    }

    public void testIsWorkingDay() {
        final WorkingWeek ww = new WorkingWeek();
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.YEAR, 2006);
        Date date = cal.getTime();
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDay(date));
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testSetWorkingDayFromCalendar() {
        WorkingWeek ww = new WorkingWeek();
        ww = ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working day
        ww = ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working day
        // do it twice
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertTrue("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww = ww.withWorkingDayFromCalendar(false, Calendar.SUNDAY); // sunday
        // working day
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww = ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working day
        // do it twice
    }

    public void testIntersection() {
        final WorkingWeek w1 = WorkingWeek.DEFAULT;
        final WorkingWeek w2 = WorkingWeek.ARABIC_WEEK;
        final WorkingWeek w3 = w1.intersection(w2);
        // working day
        Assert.assertTrue("Calendar.MONDAY", w3.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", w3.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", w3.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", w3.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertFalse("Calendar.FRIDAY", w3.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", w3.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("Calendar.SUNDAY", w3.isWorkingDayFromCalendar(Calendar.SUNDAY));
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
