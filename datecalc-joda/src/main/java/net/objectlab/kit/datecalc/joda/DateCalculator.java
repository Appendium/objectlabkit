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

import java.util.List;
import java.util.Set;

import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.LocalDate;

/**
 * A DateCalculator is a lightweight container with a reference(optional) to a
 * set of holidays, a WorkingWeek (Mon-Fri by default), a startDate and a
 * current date. The CurrentDate date is changed everytime that the addDays or
 * moveByBusinessDays methods are called.
 * 
 * @author Benoit Xhenseval
 */
public interface DateCalculator {
    /**
     * @return Calendar name (Typically the name associated with the holiday
     *         set).
     */
    String getName();

    /**
     * @param startDate
     *            the reference date for this calendar, the current date is also
     *            updated and may be moved if it falls on a non working day
     *            (holiday/weekend).
     */
    void setStartDate(final LocalDate startDate);

    /**
     * @return startDate the reference date for this calendar.
     */
    LocalDate getStartDate();

    /**
     * @param current
     *            Date held by the calendar.
     */
    LocalDate getCurrentDate();

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    boolean isWeekend(final LocalDate date);

    /**
     * is the given date a non working day, i.e. either a "weekend" or a
     * holiday?
     */
    boolean isNonWorkingDay(final LocalDate date);

    /**
     * @return true if the current date is either a weekend or a holiday.
     */
    boolean isCurrentDateNonWorking();

    /**
     * This is typically used at the construction of a DateCalculator but it
     * could be called once created and would impact only this instance of the
     * calculator; if you want to register a set of non working days for all
     * instances of calculator, one needs to do it via the
     * DateCalculatorFactory.
     * 
     * @param holidays
     *            the holiday (if null, an empty set will be put in place)
     */
    void setNonWorkingDays(final Set<LocalDate> holidays);

    /**
     * @return an immutable copy of the holiday set.
     */
    Set<LocalDate> getNonWorkingDays();

    /**
     * Allow user to define what their Working Week should be (default is
     * Mon-Fri).
     * 
     * @param week
     */
    void setWorkingWeek(final WorkingWeek week);

    /**
     * give a current business date which may be moved if it falls on a non
     * working day.
     * 
     * @param date
     * @return new current date if moved.
     */
    LocalDate setCurrentBusinessDate(final LocalDate date);

    /**
     * @return the holiday handler type, can be null
     */
    String getHolidayHandlerType();

    /**
     * move the current date by the number of days and, if it falls on a weekend
     * or holiday, move it according to the HolidayHandler given in this
     * DateCalculator.
     * 
     * @param days
     *            number of day
     * @return the businessCalendar (so one can do
     *         calendar.addDays(2).getCurrentBusinessDate();)
     */
    DateCalculator addDays(final int days);

    /**
     * move the current date by a number of business days, this means that if a
     * date is either a 'weekend' or holiday, it will be skipped acording to the
     * holiday handler and not count towards the number of days to move.
     * 
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByBusinessDays(2).getCurrentBusinessDate();)
     */
    DateCalculator moveByBusinessDays(final int businessDays);

    /**
     * move the current date by a given tenor, this means that if a date is
     * either a 'weekend' or holiday, it will be skipped acording to the holiday
     * handler and not count towards the number of days to move.
     * 
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByTenor(StandardTenor.T_2M).getCurrentBusinessDate();)
     */
    DateCalculator moveByTenor(final Tenor tenor);

    /**
     * By combining several calendars, we take into account several set of
     * holidays.
     * 
     * @param calendar
     * @return a new DateCalculator
     */
    DateCalculator combine(DateCalculator calendar);

    /**
     * @return the next IMMDate based on current date.
     */
    LocalDate getNextIMMDate();

    /**
     * @return the previous IMMDate based on current date.
     */
    LocalDate getPreviousIMMDate();

    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM.
     * 
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @return list of IMM dates
     */
    List<LocalDate> getIMMDates(final LocalDate start, final LocalDate end);
}
