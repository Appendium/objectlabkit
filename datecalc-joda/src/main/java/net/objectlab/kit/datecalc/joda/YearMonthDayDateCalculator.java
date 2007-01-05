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
import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class YearMonthDayDateCalculator extends AbstractDateCalculator<YearMonthDay> {

    private LocalDateCalculator delegate;

    @SuppressWarnings("unchecked")
    public YearMonthDayDateCalculator() {
        this(null, null, new DefaultHolidayCalendar<YearMonthDay>(Collections.EMPTY_SET), null);
    }

    /**
     * @deprecated should use the constructor with HolidayCalendar.
     * @param name
     * @param startDate
     * @param nonWorkingDays
     * @param holidayHandler
     */
    @Deprecated
    public YearMonthDayDateCalculator(final String name, final YearMonthDay startDate, final Set<YearMonthDay> nonWorkingDays,
            final HolidayHandler<YearMonthDay> holidayHandler) {
        this(name, startDate, new DefaultHolidayCalendar<YearMonthDay>(nonWorkingDays), holidayHandler);
    }

    public YearMonthDayDateCalculator(final String name, final YearMonthDay startDate,
            final HolidayCalendar<YearMonthDay> nonWorkingDays, final HolidayHandler<YearMonthDay> holidayHandler) {
        super(name, nonWorkingDays, holidayHandler);

        final Set<LocalDate> dates = new HashSet<LocalDate>();
        for (final YearMonthDay d : nonWorkingDays.getHolidays()) {
            dates.add(d.toLocalDate());
        }

        final YearMonthDay early = nonWorkingDays.getEarlyBoundary();
        final YearMonthDay late = nonWorkingDays.getLateBoundary();
        final DefaultHolidayCalendar<LocalDate> cal = new DefaultHolidayCalendar<LocalDate>(dates, early != null ? new LocalDate(
                early) : null, late != null ? new LocalDate(late) : null);

        final HolidayHandler<LocalDate> locDate = new HolidayHandlerYearMonthDayWrapper(holidayHandler, this);

        delegate = new LocalDateCalculator(name, (startDate != null ? startDate.toLocalDate() : null), cal, locDate);
        setStartDate(startDate);
    }

    // TODO throw an exception if the type is incorrect
    public void setWorkingWeek(final WorkingWeek week) {
        delegate.setWorkingWeek(week);
    }

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final YearMonthDay date) {
        if (date != null && delegate != null) {
            return delegate.isWeekend(date.toLocalDate());
        }
        return false;
    }

    public DateCalculator<YearMonthDay> moveByDays(final int days) {
        setCurrentIncrement(days);
        delegate.setCurrentIncrement(days);
        delegate.setCurrentBusinessDate(getCurrentBusinessDate().toLocalDate());
        setCurrentBusinessDate(new YearMonthDay(delegate.moveByDays(days).getCurrentBusinessDate()));
        return this;
    }

    @Override
    protected DateCalculator<YearMonthDay> createNewCalculator(final String name, final YearMonthDay startDate,
            final HolidayCalendar<YearMonthDay> holidays, final HolidayHandler<YearMonthDay> handler) {
        return new YearMonthDayDateCalculator(name, startDate, holidays, handler);
    }

    @Override
    public void setStartDate(final YearMonthDay startDate) {
        if (delegate != null) {
            delegate.setStartDate(startDate != null ? startDate.toLocalDate() : null);
        }
        super.setStartDate(startDate);
    }

    @Override
    protected YearMonthDay getToday() {
        return new YearMonthDay();
    }

    @Override
    protected DateCalculator<YearMonthDay> moveByMonths(final int months) {
        setCurrentIncrement(months);
        delegate.setCurrentIncrement(months);
        delegate.setCurrentBusinessDate(getCurrentBusinessDate().toLocalDate());
        setCurrentBusinessDate(new YearMonthDay(delegate.moveByMonths(months).getCurrentBusinessDate()));
        return this;
    }

    @Override
    protected YearMonthDay compareDate(final YearMonthDay date1, final YearMonthDay date2, final boolean returnEarliest) {
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
    protected void checkBoundary(final YearMonthDay date) {
        final YearMonthDay early = getHolidayCalendar().getEarlyBoundary();
        if (early != null && early.isAfter(date)) {
            throw new IndexOutOfBoundsException(date + " is before the early boundary " + early);
        }

        final YearMonthDay late = getHolidayCalendar().getLateBoundary();
        if (late != null && late.isBefore(date)) {
            throw new IndexOutOfBoundsException(date + " is after the late boundary " + late);
        }
    }

    @Override
    protected YearMonthDay clone(YearMonthDay date) {
        return date;
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
