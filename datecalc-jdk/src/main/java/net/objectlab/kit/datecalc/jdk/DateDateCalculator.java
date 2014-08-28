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
import java.util.Date;

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
public class DateDateCalculator extends AbstractDateCalculator<Date> {

    private final CalendarDateCalculator delegate;

    public DateDateCalculator() {
        this(null, null, new DefaultHolidayCalendar<Date>(Collections.<Date> emptySet()), null);
    }

    public DateDateCalculator(final String name, final Date startDate, final HolidayCalendar<Date> holidayCalendar,
            final HolidayHandler<Date> holidayHandler) {
        super(name, holidayCalendar, holidayHandler);
        Date date = startDate;
        final HolidayHandler<Calendar> locDate = new HolidayHandlerDateWrapper(holidayHandler, this);

        final HolidayCalendar<Calendar> nonWorkingCalendars = Utils.toHolidayCalendarSet(holidayCalendar);
        if (date == null) {
            date = getToday();
        }

        delegate = new CalendarDateCalculator(name, Utils.getCal(date), nonWorkingCalendars, locDate);
        delegate.setStartDate(Utils.getCal(date));
        if (date != null) {
            setStartDate(date);
        }
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    // TODO throw an exception if the type is incorrect
    public DateCalculator<Date> setWorkingWeek(final WorkingWeek week) {
        delegate.setWorkingWeek(week);
        return this;
    }

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final Date date) {
        if (date != null && delegate != null) {
            return delegate.isWeekend(Utils.getCal(date));
        }
        return false;
    }

    public DateCalculator<Date> moveByDays(final int days) {
        setCurrentIncrement(days);
        delegate.setCurrentIncrement(days);
        delegate.setCurrentBusinessDate(Utils.getCal(getCurrentBusinessDate()));
        setCurrentBusinessDate(delegate.moveByDays(days).getCurrentBusinessDate().getTime());
        return this;
    }

    @Override
    protected DateCalculator<Date> createNewCalculator(final String name, final Date startDate, final HolidayCalendar<Date> holidays,
            final HolidayHandler<Date> handler) {
        return new DateDateCalculator(name, startDate, holidays, handler);
    }

    @Override
    public final DateCalculator<Date> setStartDate(final Date startDate) {
        if (delegate != null) {
            delegate.setStartDate(startDate != null ? Utils.getCal(startDate) : null);
        }
        super.setStartDate(startDate);
        return this;
    }

    @Override
    protected final Date getToday() {
        return Utils.blastTime(Calendar.getInstance()).getTime();
    }

    @Override
    protected DateCalculator<Date> moveByMonths(final int months) {
        setCurrentIncrement(months);
        delegate.setCurrentIncrement(months);
        delegate.setCurrentBusinessDate(Utils.getCal(getCurrentBusinessDate()));
        setCurrentBusinessDate(delegate.moveByMonths(months).getCurrentBusinessDate().getTime());
        return this;
    }

    @Override
    protected Date compareDate(final Date date1, final Date date2, final boolean returnEarliest) {
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
    protected void checkBoundary(final Date date) {
        final Date early = getHolidayCalendar().getEarlyBoundary();
        if (early != null && early.after(date)) {
            throw new IndexOutOfBoundsException(date + " is before the early boundary " + early);
        }

        final Date late = getHolidayCalendar().getLateBoundary();
        if (late != null && late.before(date)) {
            throw new IndexOutOfBoundsException(date + " is after the late boundary " + late);
        }
    }

    @Override
    protected Date clone(final Date date) {
        return new Date(date.getTime());
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
