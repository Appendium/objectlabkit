/*
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
 */
public abstract class AbstractDateCalculator<E> implements DateCalculator<E> {

    protected static final int MONTHS_IN_QUARTER = 3;

    protected static final int MONTH_IN_YEAR = 12;

    protected static final int DAYS_IN_WEEK = 7;

    private String name;

    private E startDate;

    private E currentBusinessDate;

    private Set<E> nonWorkingDays;

    private HolidayHandler<E> holidayHandler;

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
        return startDate;
    }

    /** Set both start date and current date */
    public void setStartDate(final E startDate) {
        this.startDate = startDate;
        setCurrentBusinessDate(startDate);
    }

    public E getCurrentBusinessDate() {
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
     * @param businessDays
     * @return the current businessCalendar (so one can do
     *         calendar.moveByTenor(StandardTenor.T_2M).getCurrentBusinessDate();)
     */
    public DateCalculator<E> moveByTenor(final Tenor tenor) {
        if (tenor == null) {
            throw new IllegalArgumentException("Tenor cannot be null");
        }

        switch (tenor.getCode()) {
        case DAY:
            return moveByDays(tenor.getUnits());
        case WEEK:
            return moveByDays(tenor.getUnits() * DAYS_IN_WEEK);
        case IMM:
            setCurrentBusinessDate(getNextIMMDate());
            return this;
        default:
            throw new UnsupportedOperationException("Sorry not yet...");
        }

    }

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
        return isNonWorkingDay(currentBusinessDate);
    }

    public E setCurrentBusinessDate(final E date) {
        currentBusinessDate = date;
        if (holidayHandler != null && date != null) {
            currentBusinessDate = holidayHandler.moveCurrentDate(this);
        }
        return currentBusinessDate;
    }

    protected HolidayHandler<E> getHolidayHandler() {
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
                        HolidayHandlerType.MODIFIED_FOLLLOWING))) {
            throw new IllegalArgumentException("A " + HolidayHandlerType.MODIFIED_FOLLLOWING + " or "
                    + HolidayHandlerType.FORWARD + " does not allow negative steps for moveByBusinessDays");
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
     * @param calendar,
     *            return the same DateCalculator if calender is null or the
     *            original calendar (but why would you want to do that?)
     * @throws IllegalArgumentException
     *             if both calendars have different types of HolidayHandlers or
     *             WorkingWeek;
     */
    public DateCalculator<E> combine(final DateCalculator<E> calendar) {
        if (calendar == null || calendar == this) {
            return this;
        }

        if (holidayHandler == null && calendar.getHolidayHandlerType() != null || holidayHandler != null
                && !holidayHandler.getType().equals(calendar.getHolidayHandlerType())) {
            throw new IllegalArgumentException("Combined Calendars cannot have different handler types");
        }

        final Set<E> newSet = new HashSet<E>();
        if (nonWorkingDays != null) {
            newSet.addAll(nonWorkingDays);
        }
        if (calendar.getNonWorkingDays() != null) {
            newSet.addAll(calendar.getNonWorkingDays());
        }

        final DateCalculator<E> cal = createNewCalcultaor(getName() + "/" + calendar.getName(), getStartDate(), newSet,
                holidayHandler);

        return cal;
    }

    /**
     * @return the next IMMDate based on current date.
     */
    public E getNextIMMDate() {
        return getNextIMMDate(true, currentBusinessDate);
    }

    /**
     * @return the previous IMMDate based on current date.
     */
    public E getPreviousIMMDate() {
        return getNextIMMDate(false, currentBusinessDate);
    }

    protected abstract E getNextIMMDate(final boolean forward, final E theStartDate);

    protected abstract DateCalculator<E> createNewCalcultaor(String calcName, E theStartDate, Set<E> holidays,
            HolidayHandler<E> handler);
}
