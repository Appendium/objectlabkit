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
import java.util.List;
import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 * 
 * @author Benoit Xhenseval
 */
public class BaseDateCalculator extends AbstractDateCalculator<LocalDate> {

    private JodaWorkingWeek workingWeek = JodaWorkingWeek.DEFAULT;

    @SuppressWarnings("unchecked")
    public BaseDateCalculator() {
        this(null, null, Collections.EMPTY_SET, null);
    }

    public BaseDateCalculator(final String name, final LocalDate startDate, final Set<LocalDate> nonWorkingDays,
            final HolidayHandler<LocalDate> holidayHandler) {
        super(name, nonWorkingDays, holidayHandler);
        setStartDate(startDate);
    }

    public void setWorkingWeek(final WorkingWeek week) {
        if (week instanceof JodaWorkingWeek) {
            workingWeek = (JodaWorkingWeek) week;
        }
    }

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final LocalDate date) {
        assert workingWeek != null;
        return !workingWeek.isWorkingDay(date);
    }

    public DateCalculator<LocalDate> moveByDays(final int days) {
        if (getCurrentBusinessDate() == null) {
            initialise();
        }
        setCurrentBusinessDate(getCurrentBusinessDate().plusDays(days));

        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }

        return this;
    }

    private void initialise() {
        if (getStartDate() == null) {
            setStartDate(new LocalDate());
        } else if (getCurrentBusinessDate() == null) {
            setCurrentBusinessDate(new LocalDate());
        }
    }

    @Override
    protected DateCalculator<LocalDate> createNewCalcultaor(final String name, final LocalDate startDate,
            final Set<LocalDate> holidays, final HolidayHandler<LocalDate> handler) {
        return new BaseDateCalculator(name, startDate, holidays, handler);
    }

    public List<LocalDate> getIMMDates(final LocalDate start, final LocalDate end) {
        final List<LocalDate> dates = new ArrayList<LocalDate>();

        LocalDate date = start;
        while (true) {
            date = getNextIMMDate(true, date);
            if (!date.isAfter(end)) {
                dates.add(date);
            } else {
                break;
            }
        }

        return dates;
    }

    @Override
    protected LocalDate getNextIMMDate(final boolean forward, final LocalDate start) {
        LocalDate date = start;

        final int month = date.getMonthOfYear();
        int monthOffset = 0;

        switch (month) {
        case DateTimeConstants.MARCH:
        case DateTimeConstants.JUNE:
        case DateTimeConstants.SEPTEMBER:
        case DateTimeConstants.DECEMBER:
            final LocalDate immDate = calculate3rdWednesday(date);
            if (forward && !date.isBefore(immDate)) {
                date = date.plusMonths(MONTHS_IN_QUARTER);
            } else if (!forward && !date.isAfter(immDate)) {
                date = date.minusMonths(MONTHS_IN_QUARTER);
            }
            break;

        default:
            // Jan 1 -> 2
            // Feb 2 -> 1
            // Mar 3 -> 0
            // Apr 4 -> 2
            // May 5 -> 1
            // Jun 6 -> 0
            // Jul 7 -> 2
            // Aug 8 -> 1
            // Sep 9 -> 0
            // Oct 10 -> 2
            // Nov 11 -> 1
            // Dec 12 -> 0
            if (forward) {
                monthOffset = (MONTH_IN_YEAR - month) % MONTHS_IN_QUARTER;
                date = date.plusMonths(monthOffset);
            } else {
                monthOffset = month % MONTHS_IN_QUARTER;
                date = date.minusMonths(monthOffset);
            }
            break;
        }

        return calculate3rdWednesday(date);
    }

    /**
     * Assumes that the month is correct, get the day for the 2rd wednesday.
     * 
     * @param original
     *            the start date
     * @return the 3rd Wednesday of the month
     */
    private LocalDate calculate3rdWednesday(final LocalDate original) {
        final LocalDate firstOfMonth = original.withDayOfMonth(1);
        LocalDate firstWed = firstOfMonth.withDayOfWeek(MONTHS_IN_QUARTER);
        if (firstWed.isBefore(firstOfMonth)) {
            firstWed = firstWed.plusWeeks(1);
        }
        return firstWed.plusWeeks(2);
    }
}
