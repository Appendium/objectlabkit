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
package net.objectlab.kit.datecalc.jdk8;

import static net.objectlab.kit.datecalc.common.IMMPeriod.QUARTERLY;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;

/**
 * Joda <code>LocalDate</code> based implementation of the
 * {@link net.objectlab.kit.datecalc.common.IMMDateCalculator}.
 *
 * @author Benoit Xhenseval
 *
 */
public class LocalDateIMMDateCalculator extends AbstractIMMDateCalculator<LocalDate> {
    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM.
     *
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @param period
     *            specify when the "next" IMM is, if quarterly then it is the
     *            conventional algorithm.
     * @return list of IMM dates
     */
    @Override
    public List<LocalDate> getIMMDates(final LocalDate start, final LocalDate end, final IMMPeriod period) {
        final List<LocalDate> dates = new ArrayList<>();

        LocalDate date = start;
        while (true) {
            date = getNextIMMDate(true, date, period);
            if (!date.isAfter(end)) {
                dates.add(date);
            } else {
                break;
            }
        }

        return dates;
    }

    @Override
    protected LocalDate getNextIMMDate(final boolean requestNextIMM, final LocalDate start, final IMMPeriod period) {
        LocalDate date = start;

        date = calculateIMMMonth(requestNextIMM, date, date.getMonth());

        LocalDate imm = calculate3rdWednesday(date);
        final Month immMonth = imm.getMonth();
        final boolean isMarchSept = immMonth == Month.MARCH || immMonth == Month.SEPTEMBER;

        switch (period) {

        case BI_ANNUALY_JUN_DEC:
            if (isMarchSept) {
                imm = getNextIMMDate(requestNextIMM, imm, period);
            }
            break;

        case BI_ANNUALY_MAR_SEP:
            if (!isMarchSept) {
                imm = getNextIMMDate(requestNextIMM, imm, period);
            }
            break;

        case ANNUALLY:
            // second jump
            imm = getNextIMMDate(requestNextIMM, imm, QUARTERLY);
            // third jump
            imm = getNextIMMDate(requestNextIMM, imm, QUARTERLY);
            // fourth jump
            imm = getNextIMMDate(requestNextIMM, imm, QUARTERLY);
            // fifth jump
            imm = getNextIMMDate(requestNextIMM, imm, QUARTERLY);
            break;

        case QUARTERLY:
        default:
            break;
        }

        return imm;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    private LocalDate calculateIMMMonth(final boolean requestNextIMM, final LocalDate startDate, final Month month) {
        int monthOffset;
        LocalDate date = startDate;
        switch (month) {
        case MARCH:
        case JUNE:
        case SEPTEMBER:
        case DECEMBER:
            final LocalDate immDate = calculate3rdWednesday(date);
            if (requestNextIMM && !date.isBefore(immDate)) {
                date = date.plusMonths(MONTHS_IN_QUARTER);
            } else if (!requestNextIMM && !date.isAfter(immDate)) {
                date = date.minusMonths(MONTHS_IN_QUARTER);
            }
            break;

        default:
            if (requestNextIMM) {
                monthOffset = (MONTH_IN_YEAR - month.getValue()) % MONTHS_IN_QUARTER;
                date = date.plusMonths(monthOffset);
            } else {
                monthOffset = month.getValue() % MONTHS_IN_QUARTER;
                date = date.minusMonths(monthOffset);
            }
            break;
        }
        return date;
    }

    /**
     * Assumes that the month is correct, get the day for the 2rd wednesday.
     *
     * @param original
     *            the start date
     * @return the 3rd Wednesday of the month
     */
    private static LocalDate calculate3rdWednesday(final LocalDate original) {
        return original.with(TemporalAdjusters.firstInMonth(DayOfWeek.WEDNESDAY)).plusWeeks(2);
    }

    /**
     * Checks if a given date is an official IMM Date (3rd Wednesdays of
     * March/June/Sept/Dec.
     *
     * @param date
     * @return true if that date is an IMM date.
     */
    @Override
    public boolean isIMMDate(final LocalDate date) {
        boolean same = false;

        final List<LocalDate> dates = getIMMDates(date.minusDays(1), date, QUARTERLY);

        if (!dates.isEmpty()) {
            same = date.equals(dates.get(0));
        }

        return same;
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
