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
import java.util.GregorianCalendar;

/**
 * Convert Excel Date to Jdk <code>Date</code> or <code>Calendar</code>.
 *
 * @author Benoit Xhenseval
 *
 */
public final class ExcelDateUtil {
    private static final double HALF_MILLISEC = 0.5;

    private static final int EXCEL_FUDGE_19000229 = 61;

    private static final int EXCEL_WINDOWING_1904 = 1904;

    private static final int EXCEL_BASE_YEAR = 1900;

    private static final long DAY_MILLISECONDS = 24 * 60 * 60 * 1000L;

    private ExcelDateUtil() {
    }

    /**
     * Given an Excel date with either 1900 or 1904 date windowing, converts it
     * to a java.util.Date.
     *
     * @param excelDate
     *            The Excel date.
     * @param use1904windowing
     *            true if date uses 1904 windowing, or false if using 1900 date
     *            windowing.
     * @return Java representation of the date without any time.
     *
     * @see java.util.TimeZone
     */
    public static Calendar getJavaCalendar(final double excelDate, final boolean use1904windowing) {
        if (isValidExcelDate(excelDate)) {
            int startYear = EXCEL_BASE_YEAR;
            int dayAdjust = -1; // Excel thinks 2/29/1900 is a valid date, which
            // it isn't
            final int wholeDays = (int) Math.floor(excelDate);
            if (use1904windowing) {
                startYear = EXCEL_WINDOWING_1904;
                dayAdjust = 1; // 1904 date windowing uses 1/2/1904 as the
                // first day
            } else if (wholeDays < EXCEL_FUDGE_19000229) {
                // Date is prior to 3/1/1900, so adjust because Excel thinks
                // 2/29/1900 exists
                // If Excel date == 2/29/1900, will become 3/1/1900 in Java
                // representation
                dayAdjust = 0;
            }
            final GregorianCalendar calendar = new GregorianCalendar(startYear, 0, wholeDays + dayAdjust);
            final int millisecondsInDay = (int) ((excelDate - Math.floor(excelDate)) * DAY_MILLISECONDS + HALF_MILLISEC);
            calendar.set(Calendar.MILLISECOND, millisecondsInDay);
            return calendar;
        } else {
            return null;
        }

    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * Given an Excel date with either 1900 or 1904 date windowing, converts it
     * to a java.util.Date.
     *
     * @param excelDate
     *            The Excel date.
     * @param use1904windowing
     *            true if date uses 1904 windowing, or false if using 1900 date
     *            windowing.
     * @return Java representation of the date without any time.
     *
     * @see java.util.TimeZone
     */
    public static Date getJavaDateOnly(final double excelDate, final boolean use1904windowing) {
        final Calendar javaCalendar = getJavaCalendar(excelDate, use1904windowing);
        if (javaCalendar == null) {
            return null;
        }
        return Utils.blastTime(javaCalendar).getTime();
    }

    /**
     * Given an Excel date with either 1900 or 1904 date windowing, converts it
     * to a java.util.Date.
     *
     * NOTE: If the default <code>TimeZone</code> in Java uses Daylight Saving
     * Time then the conversion back to an Excel date may not give the same
     * value, that is the comparison <CODE>excelDate ==
     * getExcelDate(getJavaDate(excelDate,false))</CODE> is not always true.
     * For example if default timezone is <code>Europe/Copenhagen</code>, on
     * 2004-03-28 the minute after 01:59 CET is 03:00 CEST, if the excel date
     * represents a time between 02:00 and 03:00 then it is converted to past
     * 03:00 summer time
     *
     * @param excelDate
     *            The Excel date.
     * @param use1904windowing
     *            true if date uses 1904 windowing, or false if using 1900 date
     *            windowing.
     * @return Java representation of the date, or null if date is not a valid
     *         Excel date
     * @see java.util.TimeZone
     */
    public static Date getJavaDate(final double excelDate, final boolean use1904windowing) {
        final Calendar cal = getJavaCalendar(excelDate, use1904windowing);
        return cal == null ? null : cal.getTime();
    }

    /**
     * Given a double, checks if it is a valid Excel date.
     *
     * @return true if valid
     * @param excelDate
     *            the double value
     */
    public static boolean isValidExcelDate(final double excelDate) {
        return excelDate > -Double.MIN_VALUE;
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
