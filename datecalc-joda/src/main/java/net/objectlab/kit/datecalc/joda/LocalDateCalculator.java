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

import java.util.Collections;

import org.joda.time.LocalDate;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 *
 * @author Benoit Xhenseval
 *
 */
public class LocalDateCalculator extends AbstractDateCalculator<LocalDate> {

    private JodaWorkingWeek workingWeek = JodaWorkingWeek.DEFAULT;

    public LocalDateCalculator() {
        this(null, null, new DefaultHolidayCalendar<LocalDate>(Collections.<LocalDate> emptySet()), null);
    }

    public LocalDateCalculator(final String name, final LocalDate startDate, final HolidayCalendar<LocalDate> holidayCalendar,
            final HolidayHandler<LocalDate> holidayHandler) {
        super(name, holidayCalendar, holidayHandler);
        if (startDate != null) {
            setStartDate(startDate);
        }
    }

    /**
     * Set the working week.
     * @param week the JodaWorkingWeek
     * @throws IllegalArgumentException if the week is not a JodaWorkingWeek.
     */
    @Override
    public DateCalculator<LocalDate> setWorkingWeek(final WorkingWeek week) {
        if (week instanceof JodaWorkingWeek) {
            workingWeek = (JodaWorkingWeek) week;
            return this;
        }
        throw new IllegalArgumentException("Please give an instance of JodaWorkingWeek");
    }

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    @Override
    public boolean isWeekend(final LocalDate date) {
        assert workingWeek != null;
        return !workingWeek.isWorkingDay(date);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    @Override
    public DateCalculator<LocalDate> moveByDays(final int days) {
        setCurrentIncrement(days);

        setCurrentBusinessDate(getCurrentBusinessDate().plusDays(days));

        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }

        return this;
    }

    @Override
    public DateCalculator<LocalDate> moveByMonths(final int months) {
        setCurrentIncrement(months);

        setCurrentBusinessDate(getCurrentBusinessDate().plusMonths(months));

        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }

        return this;
    }

    @Override
    protected DateCalculator<LocalDate> createNewCalculator(final String name, final LocalDate startDate, final HolidayCalendar<LocalDate> holidays,
            final HolidayHandler<LocalDate> handler) {
        return new LocalDateCalculator(name, startDate, holidays, handler);
    }

    @Override
    protected LocalDate getToday() {
        return new LocalDate();
    }

    @Override
    protected LocalDate compareDate(final LocalDate date1, final LocalDate date2, final boolean returnEarliest) {
        if (date1 == null || date2 == null) {
            return null;
        }
        if (returnEarliest) {
            return date1.isAfter(date2) ? date2 : date1;
        } else {
            return date2.isAfter(date1) ? date2 : date1;
        }
    }

    @Override
    protected void checkBoundary(final LocalDate date) {
        final LocalDate early = getHolidayCalendar().getEarlyBoundary();
        if (early != null && early.isAfter(date)) {
            throw new IndexOutOfBoundsException(date + " is before the early boundary " + early);
        }

        final LocalDate late = getHolidayCalendar().getLateBoundary();
        if (late != null && late.isBefore(date)) {
            throw new IndexOutOfBoundsException(date + " is after the late boundary " + late);
        }
    }

    @Override
    protected LocalDate clone(final LocalDate date) {
        return date;
    }

    @Override
    public int getNumberOfBusinessDaysBetween(final LocalDate d1, final LocalDate d2) {
        if (d1 == null || d2 == null) {
            return 0;
        }
        final boolean d1B4d2 = !d1.isAfter(d2);
        LocalDate start = d1B4d2 ? d1 : d2;
        LocalDate end = d1B4d2 ? d2 : d1;
        if (getHolidayHandler() != null) {
            start = getHolidayHandler().adjustDate(start, 1, this);
        }

        int count = 0;
        // start = start.plusDays(1);
        while (start.isBefore(end)) {
            if (!isNonWorkingDay(start)) {
                count++;
            }
            start = start.plusDays(1);
        }
        return d1B4d2 ? count : -count;
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
