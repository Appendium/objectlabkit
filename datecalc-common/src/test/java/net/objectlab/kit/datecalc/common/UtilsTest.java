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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {
    private SimpleDateFormat sdf;

    private Set<Calendar> calendarSet;

    private Set<Date> dateSet;

    @Override
    protected void setUp() throws Exception {
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        calendarSet = new HashSet<Calendar>();
        calendarSet.add(getCal(2004, 5, 31));
        calendarSet.add(getCal(2083, 12, 1));

        dateSet = new HashSet<Date>();
        dateSet.add(getCal(2004, 5, 31).getTime());
        dateSet.add(getCal(2083, 12, 1).getTime());
    }

    private Calendar getCal(final int year, final int month, final int day) {
        final Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day, 0, 0, 0);
        return cal;
    }

    public void testGetCal() throws Exception {

        final Date d = sdf.parse("2004-02-03");
        assertEquals(Utils.getCal(d).getTime(), getCal(2004, 2, 3).getTime());

        final Date d1 = getCal(2080, 5, 31).getTime();
        final Date d2 = getCal(2080, 5, 31).getTime();
        assertEquals(Utils.getCal(d1).getTime(), d2);
    }

    public void testCreateDate() throws Exception {

        assertEquals(getCal(1970, 1, 1).getTime(), Utils.createDate("1970-01-01"));
        assertEquals(getCal(2020, 12, 31).getTime(), Utils.createDate("2020-12-31"));

        assertEquals(getCal(2006, 8, 8).getTime(), Utils.createDate("2006-08-08"));
        assertEquals(getCal(2004, 9, 12).getTime(), Utils.createDate("2004-09-12"));
    }

    public void testToCalendarSet() throws Exception {
        assertEquals(calendarSet, Utils.toCalendarSet(dateSet));
    }

    public void testToDateSet() {
        assertEquals(dateSet, Utils.toDateSet(calendarSet));
    }

    public void testToDateList() {

        final List<Date> expected = new ArrayList<Date>(dateSet);
        Collections.sort(expected);

        final List<Date> actual = Utils.toDateList(new ArrayList<Calendar>(calendarSet));
        Collections.sort(actual);
        assertEquals(expected, actual);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testFailCreateDate() {
        try {
            Utils.createDate("blablabla");
            fail("should have thown an IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // all ok
        }
    }

    public void testFailCreateCalendar() {
        try {
            Utils.createCalendar("blablabla");
            fail("should have thown an IllegalArgumentException");
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
