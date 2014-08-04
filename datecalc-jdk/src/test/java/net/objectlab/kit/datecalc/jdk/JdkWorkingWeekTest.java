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
package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.objectlab.kit.datecalc.common.WorkingWeek;

public class JdkWorkingWeekTest extends TestCase {

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

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testSetWorkingDayFromCalendar() {
        final WorkingWeek ww = new WorkingWeek();
        ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working
        // day
        ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working
        // day
        // do
        // it
        // twice
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww.withWorkingDayFromCalendar(false, Calendar.SUNDAY); // sunday
        // working
        // day
        Assert.assertTrue("2/ Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("2/ Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("2/ Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("2/ Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("2/ Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("2/ Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("2/ Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working day do it twice
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
