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
 * A DateCalculator specialised for a currency pair.
 * The Calculator also uses a HolidayHandler to determine
 * what to do when the calculated current Business Date falls on a weekend or
 * holiday (non-working day). The CurrentDate date is changed every time that the
 * moveByDays or moveBy Tenor/moveByBusinessDays methods are called. 'E' will be
 * parameterized to be a Date-like class, i.e. java.util.Date or
 * java.util.Calendar (and LocalDate or YearMonthDay for Joda-time / JDK8).
 *
 * @author Benoit Xhenseval
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay; JDK8: LocalDate
 * @since 1.4.0
 */
@Deprecated
public interface CurrencyDateCalculatorOldFashion<E> extends BaseCalculator<E> {
    /**
     * This is typically used at the construction of a DateCalculator to give a
     * reference to a Holiday Calendar, if not the case, the calculator will
     * make an immutable copy of the HolidayCalendar.
     *
     * @param ccy1Calendar
     *            the holiday calendar for ccy1 (if null, no holidays taken into account)
     * @param ccy2Calendar
     *            the holiday calendar for ccy2 (if null, no holidays taken into account)
     * @param usdCalendar
     *            the holiday calendar for USD (if null, no holidays taken into account)
     */
    CurrencyDateCalculatorOldFashion<E> setHolidayCalendars(HolidayCalendar<E> ccy1Calendar, HolidayCalendar<E> ccy2Calendar,
            HolidayCalendar<E> usdCalendar);

    /**
     * Allows user to define what their Working Weeks should be (default is
     * Mon-Fri).
     *
     * @param ccy1Week
     *            an immutable definition of a week for ccy1.
     * @param ccy2Week
     *            an immutable definition of a week for ccy1.
     * @param usdWeek
     *            an immutable definition of a week for USD.
     */
    CurrencyDateCalculatorOldFashion<E> setWorkingWeeks(WorkingWeek ccy1Week, WorkingWeek ccy2Week, WorkingWeek usdWeek);

    /**
     * This is typically the name of the associated currency pair.
     *
     * @return calculator name (Typically the name associated with the currency pair).
     */
    String getName();

    /**
     * Setting the start date also sets the current business date (and if this
     * is a non-working day, the current business date will be moved to the next
     * business day according to the HolidayHandler algorithm given).
     *
     * @param startDate
     *            the reference date for this calculator, the current business
     *            date is also updated and may be moved if it falls on a non
     *            working day (holiday/weekend).
     */
    CurrencyDateCalculatorOldFashion<E> setStartDate(E startDate);

    /**
     * Gives the startDate of this calculator (immutable once set via
     * setStartDate).
     *
     * @return startDate the reference date for this calculator.
     */
    E getStartDate();

    /**
     * Gives a current business date, it may be moved according to the
     * HolidayHandler algorithm if it falls on a non-working day.
     *
     * @param date
     * @return new current business date if moved.
     */
    E setCurrentBusinessDate(E date);

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
     *            number of days (can be &lt;0 or &gt;0)
     * @return the DateCalculator (so one can do
     *         calendar.moveByDays(-2).getCurrentBusinessDate();)
     */
    CurrencyDateCalculatorOldFashion<E> moveByDays(int days);

    /**
     * This changes the current business date held in the calculator, it moves
     * the current date by a number of business days, this means that if a date
     * is either a 'weekend' or holiday along the way, it will be skipped
     * acording to the holiday handler and not count towards the number of days
     * to move.
     *
     * @param businessDays
     *            (can be &lt;0 or &gt;0)
     * @return the current DateCalculator (so one can do
     *         calendar.moveByBusinessDays(2).getCurrentBusinessDate();)
     * @exception IllegalArgumentException
     *                if the HolidayHandlerType is (MODIFIED_PRECEDING or
     *                BACKWARD) and businessDays &gt; 0 or (MODIFIED_FOLLOWING or
     *                FORWARD) and businessDays &lt; 0
     */
    CurrencyDateCalculatorOldFashion<E> moveByBusinessDays(int businessDays);

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
    CurrencyDateCalculatorOldFashion<E> moveByTenor(Tenor tenor, int spotLag);

    /**
     * Move the current date by a given tenor, please note that all tenors are
     * relative to the CURRENT day (and NOT from spot).
     *
     * @param tenor
     *            the Tenor to reach.
     * @return the current DateCalculator
     * @since 1.1.0
     */
    CurrencyDateCalculatorOldFashion<E> moveByTenor(Tenor tenor);

    /**
     * Calculate a series of Tenor codes in one go based on SPOT day (calculated
     * with the spot lag), this does NOT change the current business date.
     *
     * @return list of dates in same order as tenors.
     */
    List<E> calculateTenorDates(List<Tenor> tenors, int spotLag);

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------
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
