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

import java.io.Serializable;
import java.util.Set;

import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;

/**
 * Factory will create new instances of calculators, these are lightweight, each
 * thread should use the factory as a given calculator should NOT be shared
 * across thread (unless you know what you're doing) as the startDate, current
 * date and working week would be shared. Once created, the set of holidays will
 * NOT change even if a new set is registered; one needs to get a new
 * DateCalculator to get the new set.
 *
 * @author Benoit Xhenseval
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 *
 */
public interface KitCalculatorsFactory<E extends Serializable> {

    /**
     * Create a new CurrencyDateCalculatorBuilder specialised for 2 currencies, including WorkingWeek, calendars registered and CurrencyCalculatorConfig.
     *
     * NOTE that USD currency holiday must also be registered.
     *
     * @param ccy1
     *            first currency, will pickup the holiday set for this ccy.
     * @param ccy2
     *            second currency, will pick up the holiday set for this ccy.
     * @param spotLag
     *            the number of days between tradeDate and spotDate.
     * @return a new CurrencyDateCalculatorBuilder
     * @exception IllegalArgumentException
     *                if the type is not null or a valid value.
     * @since 1.4.0
     */
    CurrencyDateCalculatorBuilder<E> getDefaultCurrencyDateCalculatorBuilder(String ccy1, String ccy2, SpotLag spotLag);

    CurrencyDateCalculator<E> buildCurrencyDateCalculator(CurrencyDateCalculatorBuilder<E> builder);

    /**
     * Create a new IMMUTABLE CurrencyDateCalculator specialised for 2 currencies, including WorkingWeek, calendars
     * registered and CurrencyCalculatorConfig.
     *
     * NOTE that USD currency holiday must also be registered.
     *
     * @param ccy1
     *            first currency, will pickup the holiday set for this ccy.
     * @param ccy2
     *            second currency, will pick up the holiday set for this ccy.
     * @param spotLag
     *            the number of days between tradeDate and spotDate.
     * @return a new CurrencyDateCalculator
     * @exception IllegalArgumentException
     *                if the type is not null or a valid value.
     * @since 1.4.0
     */
    CurrencyDateCalculator<E> getDefaultCurrencyDateCalculator(String ccy1, String ccy2, SpotLag spotLag);

    /**
     * Use this method register a specific currency config, if not provided then the DefaultCurrencyCalculatorConfig will be given.
     * @param config that specifies the set of currencies subject to USD T+1.
     */
    void setCurrencyCalculatorConfig(CurrencyCalculatorConfig config);

    CurrencyCalculatorConfig getCurrencyCalculatorConfig();

    /**
     * Create a new DateCalculator for a given name and type of handling.
     *
     * @param name
     *            calendar name (holidays set interested in). If there is set of
     *            holidays with that name, it will return a DateCalculator with
     *            an empty holiday set (will work on Weekend only).
     * @param holidayHandlerType
     *            typically one of the value of HolidayHandlerType or null.
     * @return a new DateCalculator
     * @exception IllegalArgumentException
     *                if the type is not null or a valid value.
     */
    DateCalculator<E> getDateCalculator(String name, String holidayHandlerType);

    /**
     * Use this method to register a holidays calendar.
     *
     * @param calendarName
     *            the calendar name to register these holidays under.
     * @param holidaysCalendar
     *            the holiday calendar (non-working days with boundaries).
     */
    KitCalculatorsFactory<E> registerHolidays(String calendarName, HolidayCalendar<E> holidaysCalendar);

    /**
     * @return true if the holiday calendar name is registered.
     */
    boolean isHolidayCalendarRegistered(String calendarName);

    /**
     * @return an immutable Holiday Calendar name that is registered.
     */
    HolidayCalendar<E> getHolidayCalendar(String calendarName);

    /**
     * @return an immutable set of registered calendar names
     */
    Set<String> getRegisteredHolidayCalendarNames();

    /**
     * Unregister a given holiday calendar
     * @param calendarName
     *          the calendar name to unregister.
     */
    KitCalculatorsFactory<E> unregisterHolidayCalendar(String calendarName);

    /**
     * unregister all holiday calendars;
     */
    KitCalculatorsFactory<E> unregisterAllHolidayCalendars();

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * Create a new PeriodCountCalculator.
     *
     * @return a PeriodCountCalculator
     */
    PeriodCountCalculator<E> getPeriodCountCalculator();

    /**
     * Create a new IMMDateCalculator.
     *
     * @return an IMMDateCalculator
     */
    IMMDateCalculator<E> getIMMDateCalculator();

    /**
     * Create a new holiday handler of given type
     * @param holidayHandlerType
     * @return a new handler
     * @throws IllegalArgumentException if the holidayHandlerType is unsupported
     * @since 1.4.0
     */
    HolidayHandler<E> getHolidayHandler(String holidayHandlerType);

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
