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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.objectlab.kit.datecalc.common.ccy.CurrencyCalculatorConfig;
import net.objectlab.kit.datecalc.common.ccy.DefaultCurrencyCalculatorConfig;

/**
 * Base class for all calculator factories, it handles the holiday registration.
 *
 * @author Marcin Jekot
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 *
 */
public abstract class AbstractKitCalculatorsFactory<E extends Serializable> implements KitCalculatorsFactory<E> {

    private final ConcurrentMap<String, HolidayCalendar<E>> holidays = new ConcurrentHashMap<String, HolidayCalendar<E>>();

    private CurrencyCalculatorConfig currencyCalculatorConfig = new DefaultCurrencyCalculatorConfig();

    /**
     * Use this method register a specific currency config, if not provided then the DefaultCurrencyCalculatorConfig will be given.
     * @param config that specifies the set of currencies subject to USD T+1 and the WorkingWeeks per currency.
     */
    public void setCurrencyCalculatorConfig(final CurrencyCalculatorConfig config) {
        currencyCalculatorConfig = config;
    }

    /**
     * Provides the registered instance of currencyCalculatorConfig or use DefaultCurrencyCalculatorConfig if none registered.
     * @see DefaultCurrencyCalculatorConfig
     */
    public CurrencyCalculatorConfig getCurrencyCalculatorConfig() {
        if (currencyCalculatorConfig == null) {
            currencyCalculatorConfig = new DefaultCurrencyCalculatorConfig();
        }
        return currencyCalculatorConfig;
    }

    /**
     * Use this method to register a given calendar, it will replace any
     * existing one with the same name. An immutable copy is made so that any changes outside this class
     * will have no affect. It won't update any existing DateCalculator as these should
     * not be amended whilst in existence (we could otherwise get inconsistent
     * results).
     *
     * @param name
     *            the calendar name to register these holidays under.
     * @param holidaysCalendar
     *            a calendar containing a set of holidays (non-working days).
     */
    public KitCalculatorsFactory<E> registerHolidays(final String name, final HolidayCalendar<E> holidaysCalendar) {
        if (name != null) {
            final Set<E> hol = new HashSet<E>();
            if (holidaysCalendar != null && holidaysCalendar.getHolidays() != null) {
                hol.addAll(holidaysCalendar.getHolidays());
            }
            final DefaultHolidayCalendar<E> defaultHolidayCalendar = new DefaultHolidayCalendar<E>(hol);
            if (holidaysCalendar != null) {
                defaultHolidayCalendar.setEarlyBoundary(holidaysCalendar.getEarlyBoundary());
                defaultHolidayCalendar.setLateBoundary(holidaysCalendar.getLateBoundary());
            }
            this.holidays.put(name, new ImmutableHolidayCalendar<E>(holidaysCalendar));
        }
        return this;
    }

    /**
     * Check if a calendar of a given name is already registered.
     * @return true if the holiday name is registered.
     */
    public boolean isHolidayCalendarRegistered(final String name) {
        return name != null && this.holidays.containsKey(name);
    }

    /**
     * Provides an immutable Holiday Calendar with that name if registered, null if not registered
     * @return an immutable Holiday Calendar that is registered, null if not registered.
     */
    public HolidayCalendar<E> getHolidayCalendar(final String name) {
        return holidays.get(name);
    }

    /**
     * Used by extensions to set holidays in a DateCalculator.
     *
     * @param name
     *            holiday name
     * @param dc
     *            the date calculator to configure.
     */
    protected void setHolidays(final String name, final DateCalculator<E> dc) {
        if (name != null) {
            dc.setHolidayCalendar(holidays.get(name));
        }
    }

    /**
     * Provides the immutable set of registered calendar names
     */
    public Set<String> getRegisteredHolidayCalendarNames() {
        return Collections.unmodifiableSet(holidays.keySet());
    }

    /**
     * Unregister a given holiday calendar
     * @param calendarName
     *          the calendar name to unregister.
     */
    public KitCalculatorsFactory<E> unregisterHolidayCalendar(final String calendarName) {
        holidays.remove(calendarName);
        return this;
    }

    /**
     * unregister all holiday calendars;
     */
    public KitCalculatorsFactory<E> unregisterAllHolidayCalendars() {
        holidays.clear();
        return this;
    }

    /**
     * Method that may be called by the specialised factory methods and will fetch the registered holidayCalendar for all 3 currencies 
     * and the working weeks via the currencyCalculatorConfig and assigning currencyCalculatorConfig to the builder, 
     * using the DefaultCurrencyCalculatorConfig if not modified.
     * @param builder the original builder
     * @return same instance of builder but modified
     */
    protected CurrencyDateCalculatorBuilder<E> configureCurrencyCalculatorBuilder(final CurrencyDateCalculatorBuilder<E> builder) {
        return builder//
                .ccy1Calendar(getHolidayCalendar(builder.getCcy1())) //
                .ccy1Week(getCurrencyCalculatorConfig().getWorkingWeek(builder.getCcy1())) //
                .ccy2Calendar(getHolidayCalendar(builder.getCcy2())) //
                .ccy2Week(getCurrencyCalculatorConfig().getWorkingWeek(builder.getCcy2())) //
                .crossCcyCalendar(getHolidayCalendar(builder.getCrossCcy())) //
                .crossCcyWeek(getCurrencyCalculatorConfig().getWorkingWeek(builder.getCrossCcy())) //
                .currencyCalculatorConfig(getCurrencyCalculatorConfig());
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
