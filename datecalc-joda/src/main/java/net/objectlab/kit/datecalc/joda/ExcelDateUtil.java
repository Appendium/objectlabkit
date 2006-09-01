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
package net.objectlab.kit.datecalc.joda;

import java.util.Date;
import java.util.GregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

/**
 * Convert Excel Date to LocalDate, YearMonthDay or DateTime.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public final class ExcelDateUtil {
    private ExcelDateUtil() {
    }

    private static final long DAY_MILLISECONDS = 24 * 60 * 60 * 1000;

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
     * @param date
     *            The Excel date.
     * @param use1904windowing
     *            true if date uses 1904 windowing, or false if using 1900 date
     *            windowing.
     * @return Java representation of the date, or null if date is not a valid
     *         Excel date
     * @see java.util.TimeZone
     */
    public static Date getJavaDate(double date, boolean use1904windowing) {
        if (isValidExcelDate(date)) {
            int startYear = 1900;
            int dayAdjust = -1; // Excel thinks 2/29/1900 is a valid date, which
            // it isn't
            int wholeDays = (int) Math.floor(date);
            if (use1904windowing) {
                startYear = 1904;
                dayAdjust = 1; // 1904 date windowing uses 1/2/1904 as the
                // first day
            } else if (wholeDays < 61) {
                // Date is prior to 3/1/1900, so adjust because Excel thinks
                // 2/29/1900 exists
                // If Excel date == 2/29/1900, will become 3/1/1900 in Java
                // representation
                dayAdjust = 0;
            }
            GregorianCalendar calendar = new GregorianCalendar(startYear, 0, wholeDays + dayAdjust);
            int millisecondsInDay = (int) ((date - Math.floor(date)) * (double) DAY_MILLISECONDS + 0.5);
            calendar.set(GregorianCalendar.MILLISECOND, millisecondsInDay);
            return calendar.getTime();
        } else {
            return null;
        }
    }

    public static LocalDate getLocalDate(double date, boolean use1904windowing) {
        return new LocalDate(getJavaDate(date, use1904windowing));
    }

    public static YearMonthDay getYearMonthDay(double date, boolean use1904windowing) {
        return new YearMonthDay(getJavaDate(date, use1904windowing));
    }

    public static DateTime getDateTime(double date, boolean use1904windowing) {
        return new DateTime(getJavaDate(date, use1904windowing));
    }

    /**
     * Given a double, checks if it is a valid Excel date.
     * 
     * @return true if valid
     * @param value
     *            the double value
     */
    public static boolean isValidExcelDate(double value) {
        return (value > -Double.MIN_VALUE);
    }
}
