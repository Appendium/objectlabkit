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

import java.util.List;

/**
 * A DateCalculator is a lightweight container with an optional reference to a
 * set of holidays, a WorkingWeek (Mon-Fri by default), a startDate and a
 * current business date. The Calculator also uses a HolidayHandler to determine
 * what to do when the calculated current Business Date falls on a weekend or
 * holiday (non-working day). The CurrentDate date is changed everytime that the
 * moveByDays or moveByBusinessDays methods are called. 'E' will be
 * parameterized to be a Date-like class, i.e. java.util.Date or
 * java.util.Calendar (and LocalDate or YearMonthDay for Joda-time).
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 * 
 */
public interface DateCalculator<E> {

    /**
     * This is typically the name of the associated set of holidays.
     * 
     * @return calculator name (Typically the name associated with the holiday
     *         set).
     */
    String getName();

    /**
     * Setting the start date also sets the current business date (and if this
     * is a non-working day, the current business date will be moved to the next
     * business day acording to the HolidayHandler algorithm given).
     * 
     * @param startDate
     *            the reference date for this calculator, the current business
     *            date is also updated and may be moved if it falls on a non
     *            working day (holiday/weekend).
     */
    void setStartDate(final E startDate);

    /**
     * Gives the startDate of this calculator (immutable once set via
     * setStartDate).
     * 
     * @return startDate the reference date for this calculator.
     */
    E getStartDate();

    /**
     * Gives the current business date held by the calculator.
     * 
     * @return a date.
     */
    E getCurrentBusinessDate();

    /**
     * Is the given date falling on a weekend, according to the WorkingWeek.
     * 
     * @return true if the date falls on a weekend.
     */
    boolean isWeekend(final E date);

    /**
     * Is the given date a non working day, i.e. either a "weekend" or a
     * holiday?
     * 
     * @return true if the given date is non-working.
     */
    boolean isNonWorkingDay(final E date);

    /**
     * Is the current business day a non-working day, this is useful if the
     * calculator does not have any algorithm to change the date when it falls
     * on a non-working day. This method can then be used to show a warning to
     * the user.
     * 
     * @return true if the current date is either a weekend or a holiday.
     */
    boolean isCurrentDateNonWorking();

    /**
     * This is typically used at the construction of a DateCalculator to give a
     * reference to a Holiday Calendar, if not the case, the calculator will 
     * make an immutable copy of the HolidayCalendar.
     * 
     * @param holidays
     *            the holiday calendar (if null, no holidays taken into account)
     * @since 1.1.0
     */
    void setHolidayCalendar(final HolidayCalendar<E> calendar);

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * Returns an immutable version of the HolidayCalendar.
     * @return a copy of the holiday calendar
     * @since 1.1.0
     */
    HolidayCalendar<E> getHolidayCalendar();

    /**
     * Allows user to define what their Working Week should be (default is
     * Mon-Fri).
     * 
     * @param week
     *            an immutable definition of a week.
     */
    void setWorkingWeek(final WorkingWeek week);

    /**
     * Gives a current business date, it may be moved acording to the
     * HolidayHandler algorithm if it falls on a non-working day.
     * 
     * @param date
     * @return new current business date if moved.
     */
    E setCurrentBusinessDate(final E date);

    /**
     * Gives the name of the holiday handler algorithm, see HolidayHandlerType
     * for some standard values.
     * 
     * @return the holiday handler type, can be null
     */
    String getHolidayHandlerType();

    /**
     * This changes the current business date held in the calculator, it moves
     * the new current business date by the number of days and, if it falls on a
     * weekend or holiday, moves it further according to the HolidayHandler
     * given in this DateCalculator.
     * 
     * @param days
     *            number of days (can be <0 or >0)
     * @return the DateCalculator (so one can do
     *         calendar.moveByDays(-2).getCurrentBusinessDate();)
     */
    DateCalculator<E> moveByDays(final int days);

    /**
     * This changes the current business date held in the calculator, it moves
     * the current date by a number of business days, this means that if a date
     * is either a 'weekend' or holiday along the way, it will be skipped
     * acording to the holiday handler and not count towards the number of days
     * to move.
     * 
     * @param businessDays
     *            (can be <0 or >0)
     * @return the current DateCalculator (so one can do
     *         calendar.moveByBusinessDays(2).getCurrentBusinessDate();)
     * @exception IllegalArgumentException
     *                if the HolidayHandlerType is (MODIFIED_PRECEDING or
     *                BACKWARD) and businessDays > 0 or (MODIFIED_FOLLOWING or
     *                FORWARD) and businessDays < 0
     */
    DateCalculator<E> moveByBusinessDays(final int businessDays);

    /**
     * Allows DateCalculators to be combined into a new one, the startDate and
     * currentBusinessDate will be the ones from the existing calendar (not the
     * parameter one). The name will be combined name1+"/"+calendar.getName().
     * If the Calendars have Early or Late boundaries, the result is the
     * narrowest interval (e.g. the later Early boundary and the earliest
     * Late boundary).
     * 
     * @param calculator
     *            return the same DateCalculator if calender is null or the
     *            original calendar (but why would you want to do that?)
     * @throws IllegalArgumentException
     *             if both calendars have different types of HolidayHandlers or
     *             WorkingWeek; Also, it is required that BOTH calendars either
     *             have Early/Late Boundaries or none.
     */
    DateCalculator<E> combine(DateCalculator<E> calculator);

    /**
     * Move the current date by a given tenor, please note that all tenors are
     * relative to the SPOT day which is a number of days from the current date.
     * This method therefore, calculates the SPOT day first, moves it if it
     * falls on a holiday and then goes to the calculated day according to the
     * Tenor.
     * 
     * @param tenor
     *            the Tenor to reach.
     * @param spotLag
     *            number of days to "spot" days, this can vary from one market
     *            to the other. It is sometimes called "settlement interval"
     *            or "offset".
     * @return the current DateCalculator
     */
    DateCalculator<E> moveByTenor(final Tenor tenor, final int spotLag);

    /**
     * Move the current date by a given tenor, please note that all tenors are
     * relative to the CURRENT day (and NOT from spot).
     * 
     * @param tenor
     *            the Tenor to reach.
     * @return the current DateCalculator
     * @since 1.1.0
     */
    DateCalculator<E> moveByTenor(final Tenor tenor);
    
    /**
     * Calculate a series of Tenor codes in one go based on current day, 
     * this does NOT change the current business date.
     * 
     * @return list of dates in same order as tenors.
     * @since 1.1.0
     */
    List<E> calculateTenorDates(final List<Tenor> tenors);
    
    /**
     * Calculate a series of Tenor codes in one go based on SPOT day (calculated
     * with the spot lag), this does NOT change the current business date.
     * 
     * @return list of dates in same order as tenors.
     * @since 1.1.0
     */
    List<E> calculateTenorDates(final List<Tenor> tenors, final int spotLag);
    
    /**
     * return the current increment in the calculator, this is used by the 
     * handler.
     */
    int getCurrentIncrement();
    
    /**
     * This would be used by delegate methods to detect if the increment
     * if positive or negative (this will allow us to define a Handler
     * that can act as Forward if positive and Backward if negative).
     * @param increment
     */
    void setCurrentIncrement(final int increment);
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
