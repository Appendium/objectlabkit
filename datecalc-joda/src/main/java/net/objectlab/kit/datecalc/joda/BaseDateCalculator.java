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

    @SuppressWarnings("unchecked")
    public BaseDateCalculator(final HolidayHandler holidayHandler) {
        this(null, null, Collections.EMPTY_SET, holidayHandler);
    }

    public BaseDateCalculator(final String name, final LocalDate startDate, final Set<LocalDate> nonWorkingDays,
            final HolidayHandler holidayHandler) {
        this.name = name;
        setStartDate(startDate);
        this.nonWorkingDays = nonWorkingDays;
        this.holidayHandler = holidayHandler;
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

    /**
     * is the given date a non working day?
     */
    public boolean isNonWorkingDay(final LocalDate date) {
        return (isWeekend(date) || nonWorkingDays.contains(date));
    }

    public boolean isCurrentDateNonWorking() {
        return isNonWorkingDay(currentDate);
    }

    public LocalDate setCurrentBusinessDate(final LocalDate date) {
        currentDate = date;
        if (holidayHandler != null && date != null) {
            currentDate = holidayHandler.moveCurrentDate(this);
        }
        return currentDate;
    }

    public DateCalculator<LocalDate> moveByDays(final int days) {
        if (currentDate == null) {
            initialise();
        }
        currentDate = currentDate.plusDays(days);

        if (holidayHandler != null) {
            currentDate = holidayHandler.moveCurrentDate(this);
        }

        return this;
    }

    private void initialise() {
        if (startDate == null) {
            setStartDate(new LocalDate());
        } else if (currentDate == null) {
            setCurrentBusinessDate(new LocalDate());
        }
    }

    public DateCalculator<LocalDate> moveByBusinessDays(final int businessDays) {
        final int numberOfStepsLeft = Math.abs(businessDays);
        final int step = (businessDays < 0 ? -1 : 1);

        for (int i = 0; i < numberOfStepsLeft; i++) {
            moveByDays(step);
        }

        return this;
    }

    /**
     * Allows DateCalculators to be combined into a new one, the startDate and
     * currentDate will be the ones from the existing calendar (not the
     * parameter one). The name will be combined name1+"/"+calendar.getName().
     * 
     * @param calendar,
     *            return the same DateCalculator if calender is null or the
     *            original calendar (but why would you want to do that?)
     * @throws IllegalArgumentException
     *             if both calendars have different types of HolidayHandlers or
     *             WorkingWeek;
     */
    public DateCalculator<LocalDate> combine(final DateCalculator calendar) {
        if (calendar == null || calendar == this) {
            return this;
        }

        if (holidayHandler == null && calendar.getHolidayHandlerType() != null || holidayHandler != null
                && !holidayHandler.getType().equals(calendar.getHolidayHandlerType())) {
            throw new IllegalArgumentException("Combined Calendars cannot have different handler types");
        }

        final Set<LocalDate> newSet = new HashSet<LocalDate>();
        if (nonWorkingDays != null) {
            newSet.addAll(nonWorkingDays);
        }
        if (calendar.getNonWorkingDays() != null) {
            newSet.addAll(calendar.getNonWorkingDays());
        }

        final DateCalculator<LocalDate> cal = new BaseDateCalculator(getName() + "/" + calendar.getName(), getStartDate(), newSet,
                holidayHandler);

        return cal;
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

    public LocalDate getNextIMMDate() {
        return getNextIMMDate(true, currentDate);
    }

    public LocalDate getPreviousIMMDate() {
        return getNextIMMDate(false, currentDate);
    }

    private LocalDate getNextIMMDate(final boolean forward, final LocalDate start) {
        LocalDate date = start;
        // Monday = 1 -> + 2 for 1st Wed + 14 | 7 + 3 - 1 = 9
        // Tuesday = 2 -> + 1 for 1st Wed + 14
        // Wednesday = 3 -> + 0 for 1st Wed + 14
        // Thursday = 4 -> + 6 for 1st Wed + 14
        // Friday = 5 -> + 5 for 1st Wed + 14
        // Saturday = 6 -> + 4 for 1st Wed + 14
        // Sunday = 7 -> + 3 for 1st Wed + 14

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
            //            
            // final int day = date.getDayOfMonth();
            // final int immDay = calculateIMMDay(date);
            // if (forward && day >= immDay || !forward && day <= immDay) {
            // monthOffset = 3;
            // if (forward) {
            // date = date.plusMonths(monthOffset);
            // } else {
            // date = date.minusMonths(monthOffset);
            // }
            // }
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

        // return date.dayOfMonth().setCopy(calculateIMMDay(date));
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
