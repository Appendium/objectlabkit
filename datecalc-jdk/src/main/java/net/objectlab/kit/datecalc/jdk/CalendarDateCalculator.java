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
import java.util.Collections;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.Utils;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 *
 * @author Marcin Jekot
 */
public class CalendarDateCalculator extends AbstractDateCalculator<Calendar> {

    private WorkingWeek workingWeek = WorkingWeek.DEFAULT;

    public CalendarDateCalculator() {
        this(null, null, new DefaultHolidayCalendar<Calendar>(Collections.<Calendar> emptySet()), null);
    }

    public CalendarDateCalculator(final String name, final Calendar startDate, final HolidayCalendar<Calendar> holidayCalendar,
            final HolidayHandler<Calendar> holidayHandler) {
        super(name, holidayCalendar, holidayHandler);
        Calendar date = startDate;
        if (date == null) {
            date = getToday();
        } else {
            setStartDate(date);
        }
    }

    public DateCalculator<Calendar> setWorkingWeek(final WorkingWeek week) {
        workingWeek = week;
        return this;
    }

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final Calendar date) {
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

    public CalendarDateCalculator moveByDays(final int days) {
        setCurrentIncrement(days);
        getCurrentBusinessDate().add(Calendar.DAY_OF_MONTH, days);

        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }

        return this;
    }

    @Override
    public DateCalculator<Calendar> moveByMonths(final int months) {
        setCurrentIncrement(months);
        final Calendar date = getCurrentBusinessDate();
        date.add(Calendar.MONTH, months);

        setCurrentBusinessDate(date);

        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }

        return this;
    }

    @Override
    protected DateCalculator<Calendar> createNewCalculator(final String name, final Calendar startDate, final HolidayCalendar<Calendar> holidays,
            final HolidayHandler<Calendar> handler) {
        return new CalendarDateCalculator(name, startDate, holidays, handler);
    }

    @Override
    protected final Calendar getToday() {
        return Utils.blastTime(Calendar.getInstance());
    }

    @Override
    protected Calendar compareDate(final Calendar date1, final Calendar date2, final boolean returnEarliest) {
        if (date1 == null || date2 == null) {
            return null;
        }
        if (returnEarliest) {
            return date1.after(date2) ? date2 : date1;
        } else {
            return date2.after(date1) ? date2 : date1;
        }
    }

    @Override
    protected void checkBoundary(final Calendar date) {
        final Calendar early = getHolidayCalendar().getEarlyBoundary();
        if (early != null && early.after(date)) {
            throw new IndexOutOfBoundsException(date + " is before the early boundary " + early);
        }

        final Calendar late = getHolidayCalendar().getLateBoundary();
        if (late != null && late.before(date)) {
            throw new IndexOutOfBoundsException(date + " is after the late boundary " + late);
        }
    }

    @Override
    protected Calendar clone(final Calendar date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date.getTime());
        return cal;
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
