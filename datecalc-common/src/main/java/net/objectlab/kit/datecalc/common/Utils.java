/*
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
import java.util.TimeZone;

/**
 * TODO javadoc
 * 
 * @author Marcin Jekot
 * @author $LastModifiedBy$
 * @version $Revision$ $Date$
 * 
 */
public class Utils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    private static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_PATTERN);

    public static Calendar getCal(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Date createDate(final String str) throws IllegalArgumentException {
        try {
            final Date date = SDF.parse(str);
            final Calendar cal = getCal(date);
            return cal.getTime();
        } catch (final ParseException e) {
            throw new IllegalArgumentException("\"" + str + "\"" + " is an invalid date, the pattern is : " + DATE_PATTERN);
        }
    }

    public static Set<Calendar> toCalendarSet(final Set<Date> dates) {
        final Set<Calendar> calendars = new HashSet<Calendar>();
        for (final Date date : dates) {
            calendars.add(getCal(date));
        }
        return calendars;
    }

    public static Set<Date> toDateSet(final Set<Calendar> calendars) {

        final Set<Date> dates = new HashSet<Date>();
        for (final Calendar calendar : calendars) {
            dates.add(calendar.getTime());
        }
        return dates;
    }

    public static List<Date> toDateList(final List<Calendar> dates) {

        final List<Date> dateList = new ArrayList<Date>();
        for (final Calendar calendar : dates) {
            dateList.add(calendar.getTime());
        }

        return dateList;
    }
}
