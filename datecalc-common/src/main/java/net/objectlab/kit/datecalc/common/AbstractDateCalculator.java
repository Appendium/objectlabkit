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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract implementation in order to encapsulate all the common functionality
 * between Jdk and Joda implementations. It is parametrized on <code><E></code>
 * but basically <code>Date</code> and <code>LocalDate</code> are the only
 * viable values for it for now.
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 * 
 */
public abstract class AbstractDateCalculator<E> implements DateCalculator<E> {
    private static final int MONTHS_IN_YEAR = 12;

    protected static final int DAYS_IN_WEEK = 7;

    private String name;

    private E startDate;

    private E currentBusinessDate;

    private Set<E> nonWorkingDays;

    private HolidayHandler<E> holidayHandler;
    
    private int currentIncrement = 0;

    protected AbstractDateCalculator(final String name, final Set<E> nonWorkingDays, final HolidayHandler<E> holidayHandler) {
        this.name = name;
        this.nonWorkingDays = nonWorkingDays;
        this.holidayHandler = holidayHandler;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public E getStartDate() {
        if (startDate == null) {
            startDate = getToday();
        }
        return startDate;
    }

    /** Set both start date and current date */
    public void setStartDate(final E startDate) {
        this.startDate = startDate;
        setCurrentBusinessDate(startDate);
    }

    public E getCurrentBusinessDate() {
        if (currentBusinessDate == null) {
            currentBusinessDate = getToday();
        }
        return currentBusinessDate;
    }

    public Set<E> getNonWorkingDays() {
        return Collections.unmodifiableSet(nonWorkingDays);
    }

    public void setNonWorkingDays(final Set<E> holidays) {
        if (holidays == null) {
            nonWorkingDays = Collections.emptySet();
        } else {
            nonWorkingDays = holidays;
        }
    }

    /**
     * move the current date by a given tenor, this means that if a date is
     * either a 'weekend' or holiday, it will be skipped acording to the holiday
     * handler and not count towards the number of days to move.
     * 
     * @param tenor the tenor.
     * @param spotLag
     *            number of days to "spot" days, this can vary from one market
     *            to the other.
     * @return the current businessCalendar (so one can do
     *         calendar.moveByTenor(StandardTenor.T_2M).getCurrentBusinessDate();)
     */
    public DateCalculator<E> moveByTenor(final Tenor tenor, final int spotLag) {
        if (tenor == null) {
            throw new IllegalArgumentException("Tenor cannot be null");
        }

        TenorCode tenorCode = tenor.getCode();
        if (tenorCode != TenorCode.OVERNIGHT) {
            // get to the Spot date first:
            moveByBusinessDays(spotLag);
        }
        int unit = tenor.getUnits();
        if (tenorCode == TenorCode.WEEK) {
            tenorCode = TenorCode.DAY;
            unit *= DAYS_IN_WEEK;
        }

        if (tenorCode == TenorCode.YEAR) {
            tenorCode = TenorCode.MONTH;
            unit *= MONTHS_IN_YEAR;
        }

        DateCalculator<E> calc;

        // move by tenor
        switch (tenorCode) {
        case OVERNIGHT:
            calc = moveByDays(1);
            break;
        case SPOT:
            calc = this;
            break;
        case DAY:
            calc = moveByDays(unit);
            break;
        case MONTH:
            calc = moveByMonths(unit);
            break;
        default:
            throw new UnsupportedOperationException("Sorry not yet...");
        }

        return calc;
    }

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    protected abstract DateCalculator<E> moveByMonths(int months);

    public void setHolidayHandler(final HolidayHandler<E> holidayHandler) {
        this.holidayHandler = holidayHandler;
    }

    public String getHolidayHandlerType() {
        return (holidayHandler != null ? holidayHandler.getType() : null);
    }

    /**
     * is the given date a non working day?
     */
    public boolean isNonWorkingDay(final E date) {
        return (isWeekend(date) || nonWorkingDays.contains(date));
    }

    public boolean isCurrentDateNonWorking() {
        if (currentBusinessDate == null) {
            currentBusinessDate = getToday();
        }
        return isNonWorkingDay(currentBusinessDate);
    }

    public E setCurrentBusinessDate(final E date) {
        currentBusinessDate = date;
        if (holidayHandler != null && date != null) {
            currentBusinessDate = holidayHandler.moveCurrentDate(this);
        }
        return currentBusinessDate;
    }

    public HolidayHandler<E> getHolidayHandler() {
        return holidayHandler;
    }

    public DateCalculator<E> moveByBusinessDays(final int businessDays) {
        if (businessDays > 0
                && holidayHandler != null
                && (holidayHandler.getType().equals(HolidayHandlerType.BACKWARD) || holidayHandler.getType().equals(
                        HolidayHandlerType.MODIFIED_PRECEEDING))) {
            throw new IllegalArgumentException("A " + HolidayHandlerType.MODIFIED_PRECEEDING + " or "
                    + HolidayHandlerType.BACKWARD + " does not allow positive steps for moveByBusinessDays");
        } else if (businessDays < 0
                && holidayHandler != null
                && (holidayHandler.getType().equals(HolidayHandlerType.FORWARD) || holidayHandler.getType().equals(
                        HolidayHandlerType.MODIFIED_FOLLOWING))) {
            throw new IllegalArgumentException("A " + HolidayHandlerType.MODIFIED_FOLLOWING + " or " + HolidayHandlerType.FORWARD
                    + " does not allow negative steps for moveByBusinessDays");
        }

        final int numberOfStepsLeft = Math.abs(businessDays);
        final int step = (businessDays < 0 ? -1 : 1);

        for (int i = 0; i < numberOfStepsLeft; i++) {
            moveByDays(step);
        }

        return this;
    }

    /**
     * Allows DateCalculators to be combined into a new one, the startDate and
     * currentBusinessDate will be the ones from the existing calendar (not the
     * parameter one). The name will be combined name1+"/"+calendar.getName().
     * 
     * @param calculator
     *            return the same DateCalculator if calender is null or the
     *            original calendar (but why would you want to do that?)
     * @throws IllegalArgumentException
     *             if both calendars have different types of HolidayHandlers or
     *             WorkingWeek;
     */
    public DateCalculator<E> combine(final DateCalculator<E> calculator) {
        if (calculator == null || calculator == this) {
            return this;
        }

        if (holidayHandler == null && calculator.getHolidayHandlerType() != null || holidayHandler != null
                && !holidayHandler.getType().equals(calculator.getHolidayHandlerType())) {
            throw new IllegalArgumentException("Combined Calendars cannot have different handler types");
        }

        final Set<E> newSet = new HashSet<E>();
        if (nonWorkingDays != null) {
            newSet.addAll(nonWorkingDays);
        }
        if (calculator.getNonWorkingDays() != null) {
            newSet.addAll(calculator.getNonWorkingDays());
        }

        final DateCalculator<E> cal = createNewCalculator(getName() + "/" + calculator.getName(), getStartDate(), newSet,
                holidayHandler);

        return cal;
    }

    protected abstract E getToday();

    protected abstract DateCalculator<E> createNewCalculator(String calcName, E theStartDate, Set<E> holidays,
            HolidayHandler<E> handler);

    /**
     * @return Returns the currentIncrement.
     */
    public int getCurrentIncrement() {
        return currentIncrement;
    }

    /**
     * @param currentIncrement The currentIncrement to set.
     */
    public void setCurrentIncrement(int currentIncrement) {
        this.currentIncrement = currentIncrement;
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
