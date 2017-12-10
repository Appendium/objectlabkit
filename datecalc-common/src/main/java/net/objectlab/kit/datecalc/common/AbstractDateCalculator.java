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

import static net.objectlab.kit.datecalc.common.HolidayHandlerType.BACKWARD;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.FORWARD;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_FOLLOWING;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_PRECEDING;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract implementation in order to encapsulate all the common functionality
 * between Jdk and Joda implementations. It is parameterized on &lt;E&gt;
 * but basically <code>Date</code> and <code>LocalDate</code> are the only
 * viable values for it for now.
 *
 * @author Marcin Jekot and Benoit Xhenseval
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 *
 */
public abstract class AbstractDateCalculator<E extends Serializable> implements DateCalculator<E> {
    private static final int MONTHS_IN_YEAR = 12;

    protected static final int DAYS_IN_WEEK = 7;

    private String name;

    private E startDate;

    private E currentBusinessDate;

    private HolidayCalendar<E> holidayCalendar;

    private HolidayHandler<E> holidayHandler;

    private int currentIncrement;

    protected AbstractDateCalculator(final String name, final HolidayCalendar<E> holidayCalendar, final HolidayHandler<E> holidayHandler) {
        this.name = name;
        if (holidayCalendar != null) {
            this.holidayCalendar = new ImmutableHolidayCalendar<E>(holidayCalendar);
        } else {
            this.holidayCalendar = new ImmutableHolidayCalendar<E>(new DefaultHolidayCalendar<E>());
        }
        this.holidayHandler = holidayHandler;
    }

    @Override
    public DateCalculator<E> setHolidayCalendar(final HolidayCalendar<E> calendar) {
        if (calendar != null) {
            if (calendar instanceof ImmutableHolidayCalendar) {
                holidayCalendar = calendar;
            } else {
                holidayCalendar = new ImmutableHolidayCalendar<E>(calendar);
            }
        } else {
            holidayCalendar = new ImmutableHolidayCalendar<E>(new DefaultHolidayCalendar<E>());
        }
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public E getStartDate() {
        if (startDate == null) {
            startDate = getToday();
        }
        return startDate;
    }

    /** Set both start date and current date */
    @Override
    public DateCalculator<E> setStartDate(final E startDate) {
        this.startDate = startDate;
        setCurrentBusinessDate(startDate);
        return this;
    }

    @Override
    public E getCurrentBusinessDate() {
        if (currentBusinessDate == null) {
            currentBusinessDate = getToday();
        }
        return currentBusinessDate;
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
    @Override
    public DateCalculator<E> moveByTenor(final Tenor tenor, final int spotLag) {
        if (tenor == null) {
            throw new IllegalArgumentException("Tenor cannot be null");
        }

        TenorCode tenorCode = tenor.getCode();
        if (tenorCode != TenorCode.OVERNIGHT && tenorCode != TenorCode.TOM_NEXT /*&& spotLag != 0*/) {
            // get to the Spot date first:
            moveToSpotDate(spotLag);
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

        return applyTenor(tenorCode, unit);
    }

    protected DateCalculator<E> applyTenor(final TenorCode tenorCode, final int unit) {
        DateCalculator<E> calc;
        // move by tenor
        switch (tenorCode) {
        case OVERNIGHT:
            calc = moveByDays(1);
            break;
        case TOM_NEXT: // it would have NOT moved by
            moveByDays(1); // calculate Tomorrow
            calc = moveByDays(1); // then the next!
            break;
        case SPOT_NEXT:
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

    /**
     * Move the current date by a given tenor, please note that all tenors are
     * relative to the CURRENT day (and NOT from spot).
     *
     * @param tenor
     *            the Tenor to reach.
     * @return the current DateCalculator
     * @since 1.1.0
     */
    @Override
    public DateCalculator<E> moveByTenor(final Tenor tenor) {
        return moveByTenor(tenor, 0);
    }

    /**
     * Calculate a series of Tenor codes in one go based on current day,
     * this does NOT change the current business date.
     *
     * @return list of dates in same order as tenors.
     * @since 1.1.0
     */
    @Override
    public List<E> calculateTenorDates(final List<Tenor> tenors) {
        return calculateTenorDates(tenors, 0);
    }

    /**
     * Calculate a series of Tenor codes in one go based on SPOT day (calculated
     * with the spot lag), this does NOT change the current business date.
     *
     * @return list of dates in same order as tenors.
     * @since 1.1.0
     */
    @Override
    public List<E> calculateTenorDates(final List<Tenor> tenors, final int spotLag) {
        final List<E> list = new ArrayList<E>();

        if (tenors != null) {
            final E originalDate = clone(getCurrentBusinessDate());
            for (final Tenor tenor : tenors) {
                moveByTenor(tenor, spotLag);
                list.add(getCurrentBusinessDate());
                setCurrentBusinessDate(originalDate);
            }
        }

        return list;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    protected abstract DateCalculator<E> moveByMonths(int months);

    public DateCalculator<E> setHolidayHandler(final HolidayHandler<E> holidayHandler) {
        this.holidayHandler = holidayHandler;
        return this;
    }

    @Override
    public String getHolidayHandlerType() {
        return holidayHandler != null ? holidayHandler.getType() : null;
    }

    /**
     * is the given date a non working day?
     */
    @Override
    public boolean isNonWorkingDay(final E date) {
        if (date != null && (holidayCalendar.getEarlyBoundary() != null || holidayCalendar.getLateBoundary() != null)) {
            checkBoundary(date);
        }
        return isWeekend(date) || holidayCalendar.isHoliday(date);
    }

    /**
     * This may throw an {@link IndexOutOfBoundsException} if the date is not within the
     * boundaries.
     * @param date
     */
    protected abstract void checkBoundary(E date);

    @Override
    public boolean isCurrentDateNonWorking() {
        if (currentBusinessDate == null) {
            currentBusinessDate = getToday();
        }
        return isNonWorkingDay(currentBusinessDate);
    }

    @Override
    public E forceCurrentDateNoAdjustment(final E date) {
        currentBusinessDate = date;
        return currentBusinessDate;
    }

    @Override
    public E setCurrentBusinessDate(final E date) {
        currentBusinessDate = date;
        if (holidayHandler != null && date != null) {
            currentBusinessDate = holidayHandler.moveCurrentDate(this);
        }
        if (date != null && (holidayCalendar.getEarlyBoundary() != null || holidayCalendar.getLateBoundary() != null)) {
            checkBoundary(date);
        }
        return currentBusinessDate;
    }

    public HolidayHandler<E> getHolidayHandler() {
        return holidayHandler;
    }

    protected void moveToSpotDate(final int spotLag) {
        moveByBusinessDays(spotLag);
    }

    @Override
    public DateCalculator<E> moveByBusinessDays(final int businessDays) {
        checkHolidayValidity(businessDays);

        final int numberOfStepsLeft = Math.abs(businessDays);
        final int step = businessDays < 0 ? -1 : 1;

        for (int i = 0; i < numberOfStepsLeft; i++) {
            moveByDays(step);
        }

        return this;
    }

    private void checkHolidayValidity(final int businessDays) {
        if (businessDays > 0 && holidayHandler != null
                && (holidayHandler.getType().equals(BACKWARD) || holidayHandler.getType().equals(MODIFIED_PRECEDING))) {
            throw new IllegalArgumentException(
                    "A " + MODIFIED_PRECEDING + " or " + BACKWARD + " does not allow positive steps for moveByBusinessDays");
        } else if (businessDays < 0 && holidayHandler != null
                && (holidayHandler.getType().equals(FORWARD) || holidayHandler.getType().equals(MODIFIED_FOLLOWING))) {
            throw new IllegalArgumentException(
                    "A " + MODIFIED_FOLLOWING + " or " + FORWARD + " does not allow negative steps for moveByBusinessDays");
        }
    }

    /**
     * Allows DateCalculators to be combined into a new one, the startDate and
     * currentBusinessDate will be the ones from the existing calendar (not the
     * parameter one). The name will be combined name1+"/"+calendar.getName().
     *
     * @param calculator
     *            return the same DateCalculator if calendar is null or the
     *            original calendar (but why would you want to do that?)
     * @throws IllegalArgumentException
     *             if both calendars have different types of HolidayHandlers or
     *             WorkingWeek;
     */
    @Override
    public DateCalculator<E> combine(final DateCalculator<E> calculator) {
        if (calculator == null || calculator == this) {
            return this;
        }

        checkHolidayHandlerValidity(calculator);

        final Set<E> newSet = new HashSet<E>();
        if (holidayCalendar != null) {
            newSet.addAll(holidayCalendar.getHolidays());
        }

        final HolidayCalendar<E> calendarToCombine = calculator.getHolidayCalendar();
        checkBoundaries(calendarToCombine);

        if (calendarToCombine.getHolidays() != null) {
            newSet.addAll(calendarToCombine.getHolidays());
        }

        final HolidayCalendar<E> newCal = new DefaultHolidayCalendar<E>(newSet,
                compareDate(holidayCalendar.getEarlyBoundary(), calendarToCombine.getEarlyBoundary(), false),
                compareDate(holidayCalendar.getLateBoundary(), calendarToCombine.getLateBoundary(), true));

        return createNewCalculator(getName() + "/" + calculator.getName(), getStartDate(), newCal, holidayHandler);
    }

    private void checkHolidayHandlerValidity(final DateCalculator<E> calculator) {
        if (holidayHandler == null && calculator.getHolidayHandlerType() != null
                || holidayHandler != null && !holidayHandler.getType().equals(calculator.getHolidayHandlerType())) {
            throw new IllegalArgumentException("Combined Calendars cannot have different handler types");
        }
    }

    private void checkBoundaries(final HolidayCalendar<E> calendarToCombine) {
        if (calendarToCombine.getEarlyBoundary() != null && holidayCalendar.getEarlyBoundary() == null
                || calendarToCombine.getEarlyBoundary() == null && holidayCalendar.getEarlyBoundary() != null) {
            throw new IllegalArgumentException("Both Calendar to be combined must either have each Early boundaries or None.");
        }

        if (calendarToCombine.getLateBoundary() != null && holidayCalendar.getLateBoundary() == null
                || calendarToCombine.getLateBoundary() == null && holidayCalendar.getLateBoundary() != null) {
            throw new IllegalArgumentException("Both Calendar to be combined must either have each Late boundaries or None.");
        }
    }

    protected abstract E getToday();

    protected abstract E compareDate(E date1, E date2, boolean returnEarliest);

    protected abstract DateCalculator<E> createNewCalculator(String calcName, E theStartDate, HolidayCalendar<E> holidays, HolidayHandler<E> handler);

    /**
     * @return Returns the currentIncrement.
     */
    @Override
    public int getCurrentIncrement() {
        return currentIncrement;
    }

    /**
     * @param currentIncrement The currentIncrement to set.
     */
    @Override
    public DateCalculator<E> setCurrentIncrement(final int currentIncrement) {
        this.currentIncrement = currentIncrement;
        return this;
    }

    /**
     * @return Returns the holidayCalendar.
     */
    @Override
    public HolidayCalendar<E> getHolidayCalendar() {
        return holidayCalendar;
    }

    protected abstract E clone(final E date);
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
