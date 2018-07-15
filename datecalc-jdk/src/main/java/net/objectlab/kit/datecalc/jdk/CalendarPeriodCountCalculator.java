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

import net.objectlab.kit.datecalc.common.CalculatorConstants;
import net.objectlab.kit.datecalc.common.PeriodCountBasis;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * Jdk <code>Calendar</code> based implementation of the
 * {@link net.objectlab.kit.datecalc.common.PeriodCountCalculator}.
 *
 * @author Marcin Jekot
 *
 */
public class CalendarPeriodCountCalculator implements PeriodCountCalculator<Calendar> {

    private static final long MILLIS_IN_DAY = 1000L * 60L * 60L * 24L;

    public int dayDiff(final Calendar start, final Calendar end, final PeriodCountBasis basis) {

        int diff;

        switch (basis) {
        case CONV_30_360:
            diff = calculateConv30360(start, end);
            break;

        case CONV_360E_ISDA:
            diff = calculateConv360EIsda(start, end);
            break;

        case CONV_360E_ISMA:
            diff = calculateConv360EIsma(start, end);
            break;

        default:
            diff = dayDiff(start, end);
        }

        return diff;
    }

    private int calculateConv360EIsma(final Calendar start, final Calendar end) {
        int diff;
        int dayStart = start.get(Calendar.DAY_OF_MONTH);
        int dayEnd = end.get(Calendar.DAY_OF_MONTH);
        if (dayEnd == CalculatorConstants.MONTH_31_DAYS) {
            dayEnd = CalculatorConstants.MONTH_30_DAYS;
        }
        if (dayStart == CalculatorConstants.MONTH_31_DAYS) {
            dayStart = CalculatorConstants.MONTH_30_DAYS;
        }
        diff = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * CalculatorConstants.YEAR_360 + (end.get(Calendar.MONTH) - start.get(Calendar.MONTH)) * CalculatorConstants.MONTH_30_DAYS
                + dayEnd - dayStart;
        return diff;
    }

    private int calculateConv360EIsda(final Calendar start, final Calendar end) {
        if (start.equals(end)) {
            return 0;
        }
        int diff;
        int dayStart = start.get(Calendar.DAY_OF_MONTH);
        int dayEnd = end.get(Calendar.DAY_OF_MONTH);
        if (start.getActualMaximum(Calendar.DAY_OF_MONTH) == dayStart) {
            dayStart = CalculatorConstants.MONTH_30_DAYS;
        }
        if (end.get(Calendar.MONTH) != Calendar.FEBRUARY && end.getActualMaximum(Calendar.DAY_OF_MONTH) == dayEnd) {
            dayEnd = CalculatorConstants.MONTH_30_DAYS;
        }

        diff = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * CalculatorConstants.YEAR_360 + (end.get(Calendar.MONTH) - start.get(Calendar.MONTH)) * CalculatorConstants.MONTH_30_DAYS
                + dayEnd - dayStart;
        return diff;
    }

    private int calculateConv30360(final Calendar start, final Calendar end) {
        int diff;
        int dayStart = start.get(Calendar.DAY_OF_MONTH);
        int dayEnd = end.get(Calendar.DAY_OF_MONTH);
        if (dayEnd == CalculatorConstants.MONTH_31_DAYS && dayStart >= CalculatorConstants.MONTH_30_DAYS) {
            dayEnd = CalculatorConstants.MONTH_30_DAYS;
        }
        if (dayStart == CalculatorConstants.MONTH_31_DAYS) {
            dayStart = CalculatorConstants.MONTH_30_DAYS;
        }
        diff = (end.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * CalculatorConstants.YEAR_360 + (end.get(Calendar.MONTH) - start.get(Calendar.MONTH)) * CalculatorConstants.MONTH_30_DAYS
                + dayEnd - dayStart;
        return diff;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    private static int dayDiff(final Calendar start, final Calendar end) {
        final long diff = Math.abs(start.getTimeInMillis() - end.getTimeInMillis());
        final double dayDiff = (double) diff / MILLIS_IN_DAY;
        return (int) Math.round(dayDiff);
    }

    public double monthDiff(final Calendar start, final Calendar end, final PeriodCountBasis basis) {
        return yearDiff(start, end, basis) * CalculatorConstants.MONTHS_IN_YEAR;
    }

    public double yearDiff(final Calendar start, final Calendar end, final PeriodCountBasis basis) {
        double diff = 0.0;

        switch (basis) {
        case ACT_ACT:
            final int startYear = start.get(Calendar.YEAR);
            final int endYear = end.get(Calendar.YEAR);
            if (startYear != endYear) {
                final Calendar endOfStartYear = (Calendar) start.clone();
                endOfStartYear.set(Calendar.DAY_OF_YEAR, endOfStartYear.getActualMaximum(Calendar.DAY_OF_YEAR));
                final Calendar startOfEndYear = (Calendar) end.clone();
                startOfEndYear.set(Calendar.DAY_OF_YEAR, startOfEndYear.getActualMinimum(Calendar.DAY_OF_YEAR));

                final int diff1 = dayDiff(start, endOfStartYear);
                final int diff2 = dayDiff(startOfEndYear, end);

                diff = (diff1 + 1.0) / start.getActualMaximum(Calendar.DAY_OF_YEAR) + (endYear - startYear - 1.0)
                        + diff2 / (double) end.getActualMaximum(Calendar.DAY_OF_YEAR);
            }
            break;

        case CONV_30_360:
        case CONV_360E_ISDA:
        case CONV_360E_ISMA:
        case ACT_360:
            diff = dayDiff(start, end, basis) / CalculatorConstants.YEAR_360_0;
            break;

        case ACT_365:
            diff = dayDiff(start, end, basis) / CalculatorConstants.YEAR_365_0;
            break;

        default:
            throw new UnsupportedOperationException("Sorry no ACT_UST yet");
        }

        return diff;
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
