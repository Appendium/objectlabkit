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

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract implementation in order to encapsulate all the common functionality
 * between Jdk and Joda implementations. It is parameterized on &lt;E&gt;
 * but basically <code>Date</code> and <code>LocalDate</code> are the only
 * viable values for it for now.
 *
 * @author Marcin Jekot
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 *
 */
public abstract class AbstractIMMDateCalculator<E> implements IMMDateCalculator<E> {

    protected static final int MONTHS_IN_QUARTER = 3;

    protected static final int MONTH_IN_YEAR = 12;

    protected static final int DAYS_IN_WEEK = 7;

    /**
     * @param startDate
     * @return the next IMMDate based on current date.
     */
    public E getNextIMMDate(final E startDate) {
        return getNextIMMDate(true, startDate, IMMPeriod.QUARTERLY);
    }

    /**
     * @param startDate
     * @param period
     *            specify when the "next" IMM is, if quarterly then it is the
     *            conventional algorithm.
     * @return the next IMMDate based on current date.
     */
    public E getNextIMMDate(final E startDate, final IMMPeriod period) {
        return getNextIMMDate(true, startDate, period);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * @param startDate
     * @return the previous IMMDate based on current date.
     */
    public E getPreviousIMMDate(final E startDate) {
        return getNextIMMDate(false, startDate, IMMPeriod.QUARTERLY);
    }

    /**
     * @param startDate
     * @param period
     *            specify when the "previous" IMM is, if quarterly then it is
     *            the conventional algorithm.
     * @return the previous IMMDate based on current date.
     */
    public E getPreviousIMMDate(final E startDate, final IMMPeriod period) {
        return getNextIMMDate(false, startDate, period);
    }

    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM
     * (same as IMMPeriod.QUARTERLY).
     *
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @return list of IMM dates
     */
    public List<E> getIMMDates(final E start, final E end) {
        return getIMMDates(start, end, IMMPeriod.QUARTERLY);
    }

    /**
     * Returns a list of N IMM dates from a given date, it will exclude the start
     * date if it is an IMM date
     * (same as as calling getIMMDates(start,end,IMMPeriod.QUARTERLY)).
     *
     * @param start
     *            start of the interval, excluded
     * @param numberOfDates
     *            number of IMM dates to return.
     * @return list of IMM dates
     * @since 1.4.0
     */
    public List<E> getNextIMMDates(final E start, final int numberOfDates) {
        if (numberOfDates < 0) {
            throw new IllegalArgumentException("numberOfDates cannot be < 0 (" + numberOfDates + ")");
        }
        final List<E> dates = new ArrayList<E>(numberOfDates);

        E date = start;
        for (int i = 0; i < numberOfDates; i++) {
            date = getNextIMMDate(true, date, IMMPeriod.QUARTERLY);
            dates.add(date);
        }

        return dates;
    }

    protected abstract E getNextIMMDate(final boolean requestNextIMM, final E theStartDate, final IMMPeriod period);
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
