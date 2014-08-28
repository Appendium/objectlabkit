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

import net.objectlab.kit.datecalc.common.ExcelDateUtil;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * Convert Excel Date to LocalDate, YearMonthDay or DateTime.
 *
 * @author Benoit Xhenseval
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

        return new LocalDate().withYear(c.get(Calendar.YEAR)).withMonthOfYear(c.get(Calendar.MONTH) + 1).withDayOfMonth(c.get(Calendar.DAY_OF_MONTH));
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public static DateTime getDateTime(final double date, final boolean use1904windowing) {
        final Calendar c = ExcelDateUtil.getJavaCalendar(date, use1904windowing);

        if (c == null) {
            return null;
        }

        return new DateTime().withYear(c.get(Calendar.YEAR)).withMonthOfYear(c.get(Calendar.MONTH) + 1).withDayOfMonth(c.get(Calendar.DAY_OF_MONTH))
                .withMillisOfDay(0);
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
