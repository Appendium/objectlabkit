/*
 * $Id: JodaExcelDateUtil.java 99 2006-09-04 20:30:25Z marchy $
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

import net.objectlab.kit.datecalc.common.ExcelDateUtil;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

/**
 * Convert Excel Date to LocalDate, YearMonthDay or DateTime.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy: marchy $
 * @version $Revision: 99 $ $Date: 2006-09-04 21:30:25 +0100 (Mon, 04 Sep 2006) $
 * 
 */
public final class JodaExcelDateUtil {

    private JodaExcelDateUtil() {
    }

    public static LocalDate getLocalDate(final double date, final boolean use1904windowing) {
        final Calendar c = ExcelDateUtil.getJavaCalendar(date, use1904windowing);
        
        if (c == null) {
            return null;
        }
        
        return new LocalDate()
                .withYear(c.get(Calendar.YEAR))
                .withMonthOfYear(c.get(Calendar.MONTH) + 1)
                .withDayOfMonth(c.get(Calendar.DAY_OF_MONTH));
    }

    public static YearMonthDay getYearMonthDay(final double date, final boolean use1904windowing) {
        final Calendar c = ExcelDateUtil.getJavaCalendar(date, use1904windowing);
        
        if (c == null) {
            return null;
        }
        
        return new YearMonthDay(c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH));
    }

    public static DateTime getDateTime(final double date, final boolean use1904windowing) {
        final Calendar c = ExcelDateUtil.getJavaCalendar(date, use1904windowing);
        
        if (c == null) {
            return null;
        }
        
        return new DateTime()
                .withYear(c.get(Calendar.YEAR))
                .withMonthOfYear(c.get(Calendar.MONTH) + 1)
                .withDayOfMonth(c.get(Calendar.DAY_OF_MONTH))
                .withMillisOfDay(0);
    }
}
