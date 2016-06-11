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

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DECEMBER;
import static java.util.Calendar.JUNE;
import static java.util.Calendar.MARCH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SEPTEMBER;
import static java.util.Calendar.WEDNESDAY;
import static net.objectlab.kit.datecalc.common.IMMPeriod.QUARTERLY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;

/**
 * Jdk Calendar implementation of the
 * {@link net.objectlab.kit.datecalc.common.IMMDateCalculator}
 *
 * @author Marcin Jekot
 *
 */
public class CalendarIMMDateCalculator extends AbstractIMMDateCalculator<Calendar> {
    private static final int NUMBER_DAYS_IN_WEEK = 7;

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
    public List<Calendar> getIMMDates(final Calendar start, final Calendar end, final IMMPeriod period) {

        final List<Calendar> dates = new ArrayList<Calendar>();
        Calendar cal = (Calendar) start.clone();
        while (true) {
            cal = getNextIMMDate(true, cal, period);
            if (!cal.after(end)) {
                dates.add(cal);
            } else {
                break;
            }
        }

        return dates;
    }

    @Override
    protected Calendar getNextIMMDate(final boolean requestNextIMM, final Calendar startDate, final IMMPeriod period) {

        Calendar cal = (Calendar) startDate.clone();

        if (isIMMMonth(cal)) {
            moveToIMMDay(cal);
            if (requestNextIMM && cal.after(startDate) || !requestNextIMM && cal.before(startDate)) {
                return cal;
            }
        }

        final int delta = requestNextIMM ? 1 : -1;
        do {
            cal.add(MONTH, delta);
        } while (!isIMMMonth(cal));

        moveToIMMDay(cal);

        cal = handlePeriod(requestNextIMM, period, cal);

        return cal;
    }

    private Calendar handlePeriod(final boolean requestNextIMM, final IMMPeriod period, final Calendar givenCal) {
        Calendar cal = givenCal;
        final int month = cal.get(MONTH);
        switch (period) {
        case BI_ANNUALY_JUN_DEC:
            if (month == MARCH || month == SEPTEMBER) {
                // need to move to the next one.
                cal = getNextIMMDate(requestNextIMM, cal, period);
            }
            break;

        case BI_ANNUALY_MAR_SEP:
            if (month == JUNE || month == DECEMBER) {
                // need to move to the next one.
                cal = getNextIMMDate(requestNextIMM, cal, period);
            }
            break;

        case ANNUALLY:
            // second jump
            cal = getNextIMMDate(requestNextIMM, cal, QUARTERLY);
            // third jump
            cal = getNextIMMDate(requestNextIMM, cal, QUARTERLY);
            // fourth jump
            cal = getNextIMMDate(requestNextIMM, cal, QUARTERLY);
            // fifth jump
            cal = getNextIMMDate(requestNextIMM, cal, QUARTERLY);
            break;

        case QUARTERLY:
        default:
            break;
        }
        return cal;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    private static boolean isIMMMonth(final Calendar cal) {
        final int month = cal.get(MONTH);

        switch (month) {
        case MARCH:
        case JUNE:
        case SEPTEMBER:
        case DECEMBER:
            return true;
        default:
            return false;
        }
    }

    /**
     * Assumes that the month is correct, get the day for the 3rd wednesday.
     *
     * @param cal
     */
    private static void moveToIMMDay(final Calendar cal) {
        cal.set(DAY_OF_MONTH, 1);

        // go to 1st wed
        final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek < WEDNESDAY) {
            cal.add(DAY_OF_MONTH, WEDNESDAY - dayOfWeek);
        } else if (dayOfWeek > WEDNESDAY) {
            cal.add(DAY_OF_MONTH, WEDNESDAY + NUMBER_DAYS_IN_WEEK - dayOfWeek);
        }

        // go to 3rd wednesday - i.e. move 2 weeks forward
        cal.add(DAY_OF_MONTH, NUMBER_DAYS_IN_WEEK * 2);
    }

    public boolean isIMMDate(final Calendar date) {
        // TODO a slightly crude implementation - revisit
        final Calendar cal = (Calendar) date.clone();
        moveToIMMDay(cal);
        return cal.equals(date);
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
