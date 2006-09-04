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
package net.objectlab.kit.datecalc.joda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.IMMPeriod;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 * 
 * @author Benoit Xhenseval
 */
public class YearMonthDayDateCalculator extends AbstractDateCalculator<YearMonthDay> {

    private LocalDateCalculator delegate;

    @SuppressWarnings("unchecked")
    public YearMonthDayDateCalculator() {
        this(null, null, Collections.EMPTY_SET, null);
    }

    public YearMonthDayDateCalculator(final String name, final YearMonthDay startDate, final Set<YearMonthDay> nonWorkingDays,
            final HolidayHandler<YearMonthDay> holidayHandler) {
        super(name, nonWorkingDays, holidayHandler);

        final Set<LocalDate> dates = new HashSet<LocalDate>();
        for (final YearMonthDay d : nonWorkingDays) {
            dates.add(d.toLocalDate());
        }

        final HolidayHandler<LocalDate> locDate = new HolidayHandlerYearMonthDayWrapper(holidayHandler, this);

        delegate = new LocalDateCalculator(name, (startDate != null ? startDate.toLocalDate() : null), dates, locDate);
        setStartDate(startDate);
    }

    // TODO throw an exception if the type is incorrect
    public void setWorkingWeek(final WorkingWeek week) {
        delegate.setWorkingWeek(week);
    }

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final YearMonthDay date) {
        if (date != null) {
            return delegate.isWeekend(date.toLocalDate());
        }
        return false;
    }

    public DateCalculator<YearMonthDay> moveByDays(final int days) {
        delegate.setCurrentBusinessDate(getCurrentBusinessDate().toLocalDate());
        setCurrentBusinessDate(new YearMonthDay(delegate.moveByDays(days).getCurrentBusinessDate()));
        return this;
    }

    @Override
    protected DateCalculator<YearMonthDay> createNewCalcultaor(final String name, final YearMonthDay startDate,
            final Set<YearMonthDay> holidays, final HolidayHandler<YearMonthDay> handler) {
        return new YearMonthDayDateCalculator(name, startDate, holidays, handler);
    }

    @Override
    public List<YearMonthDay> getIMMDates(final YearMonthDay start, final YearMonthDay end) {
        final List<YearMonthDay> dates = new ArrayList<YearMonthDay>();
        final List<LocalDate> localDates = delegate.getIMMDates(start.toLocalDate(), end.toLocalDate());

        for (final LocalDate d : localDates) {
            dates.add(new YearMonthDay(d));
        }

        return dates;
    }

    @Override
    protected YearMonthDay getNextIMMDate(final boolean forward, final YearMonthDay start, final IMMPeriod period) {
        return new YearMonthDay(delegate.getNextIMMDate(forward, start.toLocalDate(), period));
    }

    @Override
    public void setStartDate(final YearMonthDay startDate) {
        if (delegate != null) {
            delegate.setStartDate(startDate != null ? startDate.toLocalDate() : null);
        }
        super.setStartDate(startDate);
    }

    public List<YearMonthDay> getIMMDates(final YearMonthDay start, final YearMonthDay end, final IMMPeriod period) {
        final List<YearMonthDay> dates = new ArrayList<YearMonthDay>();
        final List<LocalDate> d = delegate.getIMMDates(start.toLocalDate(), end.toLocalDate(), period);
        for (final LocalDate date : d) {
            dates.add(new YearMonthDay(date));
        }
        return dates;
    }

    public boolean isIMMDate(final YearMonthDay date) {
        return delegate.isIMMDate(date.toLocalDate());
    }

}
