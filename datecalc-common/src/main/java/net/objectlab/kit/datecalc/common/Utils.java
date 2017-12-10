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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utilities class for <code>Date/Calendar</code> conversions
 *
 * @author Marcin Jekot
 *
 */
public final class Utils {
    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private Utils() {
    }

    /**
     * Removes set's all "time" fields to zero, leaving only the date portion of
     * the Calendar. The Calendar passe
     *
     * @param cal
     *            to Calendar object to blast, note, it will be modified
     * @return the calendar object modified (same instance)
     */
    public static Calendar blastTime(final Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    /**
     * Creates a Date object given a string representation of it
     *
     * @param dateStr
     *            string (return today if string is null)
     * @return today if string is null, a Date object representing the string
     *         otherwise
     * @throws IllegalArgumentException
     *             if the string cannot be parsed.
     */
    public static Date createDate(final String dateStr) {
        if (dateStr == null) {
            return createCalendar(null).getTime();
        }
        final Calendar cal = getCal(dateStr);
        return cal != null ? cal.getTime() : null;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * get a new Calendar based on the string date.
     * @param dateStr
     *            the date string
     * @return a new Calendar
     * @throws IllegalArgumentException
     *             if the string cannot be parsed.
     */
    public static Calendar createCalendar(final String dateStr) {
        if (dateStr == null) {
            return blastTime(Calendar.getInstance());
        }
        return getCal(dateStr);
    }

    public static Calendar getCal(final String dateStr) {
        try {
            final Date date = new SimpleDateFormat(DATE_PATTERN).parse(dateStr);
            return getCal(date);
        } catch (final ParseException e) {
            throw new IllegalArgumentException("\"" + dateStr + "\"" + " is an invalid date, the pattern is : " + DATE_PATTERN, e);
        }
    }

    /**
     * Get a Calendar object for a given Date representation.
     *
     * @param date
     * @return the Calendar
     */
    public static Calendar getCal(final Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return blastTime(cal);
    }

    /**
     * Converts a Set of Date objects to a Set of Calendar objects.
     *
     * @param dates
     * @return the converted Set&lt;Calendar&gt;
     */
    public static Set<Calendar> toCalendarSet(final Set<Date> dates) {
        final Set<Calendar> calendars = new HashSet<Calendar>();
        for (final Date date : dates) {
            calendars.add(getCal(date));
        }
        return calendars;
    }

    /**
     * Converts a Set of Date objects to a Set of Calendar objects.
     *
     * @param dates
     * @return the converted Set&lt;Calendar&gt;
     */
    public static HolidayCalendar<Calendar> toHolidayCalendarSet(final HolidayCalendar<Date> dates) {
        final Set<Calendar> calendars = new HashSet<Calendar>();
        for (final Date date : dates.getHolidays()) {
            calendars.add(getCal(date));
        }
        return new DefaultHolidayCalendar<Calendar>(calendars, getCal(dates.getEarlyBoundary()), getCal(dates.getLateBoundary()));
    }

    /**
     * Converts a Set of Calendar objects to a Set of Date objects
     *
     * @param calendars
     * @return the converset Set&lt;Date&gt;
     */
    public static Set<Date> toDateSet(final Set<Calendar> calendars) {

        final Set<Date> dates = new HashSet<Date>();
        for (final Calendar calendar : calendars) {
            dates.add(calendar.getTime());
        }
        return dates;
    }

    /**
     * Converts a <code>List</code> of Calendar objects to a <code>List</code>
     * of dates
     *
     * @param dates
     * @return the converted List&lt;Date&gt;
     */
    public static List<Date> toDateList(final List<Calendar> dates) {

        final List<Date> dateList = new ArrayList<Date>();
        for (final Calendar calendar : dates) {
            dateList.add(calendar.getTime());
        }

        return dateList;
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
