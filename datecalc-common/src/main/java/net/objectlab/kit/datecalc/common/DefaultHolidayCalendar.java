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
 * $Id: DateCalculator.java 200 2006-10-10 20:15:58Z benoitx $
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author $LastChangedBy: marchy $
 *
 */
public class DefaultHolidayCalendar<E extends Serializable> implements HolidayCalendar<E> {
    private static final long serialVersionUID = -8558686840806739645L;

    /**
     * Changed to a Map of String to E, given the JODA issue 
     * http://joda-interest.219941.n2.nabble.com/LocalDate-equals-method-bug-td7572429.html
     * @since 1.4.0
     */
    private Map<String, E> holidays;

    private E earlyBoundary;

    private E lateBoundary;

    public DefaultHolidayCalendar() {
        super();
        holidays = Collections.emptyMap();
    }

    public DefaultHolidayCalendar(final Set<E> holidays, final E earlyBoundary, final E lateBoundary) {
        super();
        setHolidays(holidays);

        this.earlyBoundary = earlyBoundary;
        this.lateBoundary = lateBoundary;
    }

    public DefaultHolidayCalendar(final Set<E> holidays) {
        super();
        setHolidays(holidays);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getEarlyBoundary()
     */
    public E getEarlyBoundary() {
        return earlyBoundary;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getHolidays()
     */
    public Set<E> getHolidays() {
        return new HashSet<E>(holidays.values());
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getLateBoundary()
     */
    public E getLateBoundary() {
        return lateBoundary;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setEarlyBoundary(java.lang.Object)
     */
    public HolidayCalendar<E> setEarlyBoundary(final E earlyBoundary) {
        this.earlyBoundary = earlyBoundary;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setHolidays(java.util.Set)
     */
    public final HolidayCalendar<E> setHolidays(final Set<E> holidays) {

        if (holidays == null) {
            this.holidays = Collections.emptyMap();
            return this;
        }

        final Map<String, E> newSet = new TreeMap<String, E>();
        for (final E e : holidays) {
            newSet.put(toString(e), e);
        }
        this.holidays = Collections.unmodifiableMap(newSet);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setLateBoundary(java.lang.Object)
     */
    public HolidayCalendar<E> setLateBoundary(final E lateBoundary) {
        this.lateBoundary = lateBoundary;
        return this;
    }

    public boolean isHoliday(final E date) {
        return holidays.containsKey(toString(date));
    }

    private String toString(final E date) {
        if (date instanceof Calendar) {
            return new SimpleDateFormat("yyyy-MM-dd").format(((Calendar) date).getTime());
        } else if (date instanceof Date) {
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        }

        return date != null ? date.toString() : "";
    }
}
