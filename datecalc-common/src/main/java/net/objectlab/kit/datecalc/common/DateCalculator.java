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
package net.objectlab.kit.datecalc.common;

import java.util.List;
import java.util.Set;

/**
 * A DateCalculator is a lightweight container with a reference(optional) to a
 * set of holidays, a WorkingWeek (Mon-Fri by default), a startDate and a
 * current date. The CurrentDate date is changed everytime that the addDays or
 * moveByBusinessDays methods are called.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$
 * $Date$
 * $LastChangedDate$
 * $Date: $
 * $LastChangedDate: $
 */
public interface DateCalculator<E> {

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
    void setStartDate(final E startDate);

    /**
     * @return startDate the reference date for this calendar.
     */
    E getStartDate();

    /**
     * @param currentDate
     *            held by the calendar.
     */
    E getCurrentBusinessDate();

    /**
     * is the given date on a weekend, according to the WorkingWeek
     */
    boolean isWeekend(final E date);

    /**
     * is the given date a non working day, i.e. either a "weekend" or a
     * holiday?
     */
    boolean isNonWorkingDay(final E date);

    /**
     * @return true if the current date is either a weekend or a holiday.
     */
    boolean isCurrentDateNonWorking();

    /**
     * This is typically used at the construction of a DateCalculator.
     * 
     * @param holidays
     *            the holiday (if null, an empty set will be put in place)
     */
    void setNonWorkingDays(final Set<E> holidays);

    /**
     * @return an immutable copy of the holiday set.
     */
    Set<E> getNonWorkingDays();

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
    E setCurrentBusinessDate(final E date);

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
     *         calendar.moveByDays(-2).getCurrentBusinessDate();)
     */
    DateCalculator<E> moveByDays(final int days);

    /**
     * move the current date by a number of business days, this means that if a
     * date is either a 'weekend' or holiday, it will be skipped acording to the
     * holiday handler and not count towards the number of days to move.
     * 
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByBusinessDays(2).getCurrentBusinessDate();)
     */
    DateCalculator<E> moveByBusinessDays(final int businessDays);

    /**
     * By combining several calendars, we take into account several set of
     * holidays.
     * 
     * @param calendar
     * @return a new DateCalculator
     */
    DateCalculator<E> combine(DateCalculator<E> calendar);

    /**
     * move the current date by a given tenor, this means that if a date is
     * either a 'weekend' or holiday, it will be skipped acording to the holiday
     * handler and not count towards the number of days to move.
     * 
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByTenor(StandardTenor.T_2M).getCurrentBusinessDate();)
     */
    DateCalculator<E> moveByTenor(final Tenor tenor);

    /**
     * @return the next IMMDate based on current date.
     */
    E getNextIMMDate();

    /**
     * @return the previous IMMDate based on current date.
     */
    E getPreviousIMMDate();

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
    List<E> getIMMDates(final E start, final E end);
}