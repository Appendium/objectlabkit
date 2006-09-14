/*
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
 * @author $LastModifiedBy$
 * @version $Revision$ $Date$
 * 
 */
public final class Utils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN);

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
        try {
            final Date date = SDF.parse(dateStr);
            final Calendar cal = getCal(date);
            return cal.getTime();
        } catch (final ParseException e) {
            throw new IllegalArgumentException("\"" + dateStr + "\"" + " is an invalid date, the pattern is : " + DATE_PATTERN);
        }
    }

    /**
     * 
     * @param str
     *            string
     * @return
     * @throws IllegalArgumentException
     *             if the string cannot be parsed.
     */
    public static Calendar createCalendar(final String str) {
        if (str == null) {
            return blastTime(Calendar.getInstance());
        }
        try {
            final Date date = SDF.parse(str);
            final Calendar cal = getCal(date);
            return cal;
        } catch (final ParseException e) {
            throw new IllegalArgumentException("\"" + str + "\"" + " is an invalid date, the pattern is : " + DATE_PATTERN);
        }
    }

    /**
     * Get a Calendar object for a given Date representation
     * 
     * @param date
     * @return
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
     * Converts a Set of Date objects to a Set of Calendar objects
     * 
     * @param dates
     * @return
     */
    public static Set<Calendar> toCalendarSet(final Set<Date> dates) {
        final Set<Calendar> calendars = new HashSet<Calendar>();
        for (final Date date : dates) {
            calendars.add(getCal(date));
        }
        return calendars;
    }

    /**
     * Converts a Set of Calendar objects to a Set of Date objects
     * 
     * @param calendars
     * @return
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
     * @return
     */
    public static List<Date> toDateList(final List<Calendar> dates) {

        final List<Date> dateList = new ArrayList<Date>();
        for (final Calendar calendar : dates) {
            dateList.add(calendar.getTime());
        }

        return dateList;
    }
}
