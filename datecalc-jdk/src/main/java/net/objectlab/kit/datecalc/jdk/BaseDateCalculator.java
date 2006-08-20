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
package net.objectlab.kit.datecalc.jdk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 * 
 * @author Benoit Xhenseval
 */
public class BaseDateCalculator implements DateCalculator {
    
    private String name;

    private Date startDate;

    private Date currentDate;

    private Set<Date> nonWorkingDays;

    private WorkingWeek workingWeek = WorkingWeek.DEFAULT;

    private HolidayHandler holidayHandler = null;

    private final int DAYS_IN_WEEK = 7;
    
    @SuppressWarnings("unchecked")
    public BaseDateCalculator() {
        this(null, null, Collections.EMPTY_SET, null);
    }

    @SuppressWarnings("unchecked")
    public BaseDateCalculator(final HolidayHandler holidayHandler) {
        this(null, null, Collections.EMPTY_SET, holidayHandler);
    }

    public BaseDateCalculator(final String name, final Date startDate, final Set<Date> nonWorkingDays,
            final HolidayHandler holidayHandler) {
        this.name = name;
        setStartDate(startDate);
        this.nonWorkingDays = nonWorkingDays;
        this.holidayHandler = holidayHandler;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public Set<Date> getNonWorkingDays() {
        return Collections.unmodifiableSet(nonWorkingDays);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setNonWorkingDays(final Set<Date> holidays) {
        if (holidays == null) {
            nonWorkingDays = Collections.emptySet();
        } else {
            nonWorkingDays = holidays;
        }
    }

    /** Set both start date and current date */
    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
        setCurrentBusinessDate(startDate);
    }

    public void setWorkingWeek(final WorkingWeek week) {
        workingWeek = week;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    void setHolidayHandler(final HolidayHandler holidayHandler) {
        this.holidayHandler = holidayHandler;
    }

    public String getHolidayHandlerType() {
        return (holidayHandler != null ? holidayHandler.getType() : null);
    }

    // -----------------------------------------------------------------

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final Date date) {
        assert workingWeek != null;
        return !workingWeek.isWorkingDay(date);
    }

    /**
     * is the given date a non working day?
     */
    public boolean isNonWorkingDay(final Date date) {
        return (isWeekend(date) || nonWorkingDays.contains(date));
    }

    public boolean isCurrentDateNonWorking() {
        return isNonWorkingDay(currentDate);
    }

    public Date setCurrentBusinessDate(final Date date) {
        currentDate = date;
        if (holidayHandler != null && date != null) {
            currentDate = holidayHandler.moveCurrentDate(this);
        }
        return currentDate;
    }

    public DateCalculator addDays(final int days) {
        if (currentDate == null) {
            initialise();
        }
        
        Calendar cal = Utils.getCal(currentDate);
        cal.add(Calendar.DAY_OF_MONTH, days);
        setCurrentBusinessDate(cal.getTime());
        
        return this;
    }

    private void initialise() {
        if (startDate == null) {
            setStartDate(new Date());
        } else if (currentDate == null) {
            setCurrentBusinessDate(new Date());
        }
    }

    public DateCalculator moveByBusinessDays(final int businessDays) {
        final int numberOfStepsLeft = Math.abs(businessDays);
        final int step = (businessDays < 0 ? -1 : 1);

        for (int i = 0; i < numberOfStepsLeft; i++) {
            addDays(step);
        }

        return this;
    }

    /**
     * Allows DateCalculators to be combined into a new one, the startDate and
     * currentDate will be the ones from the existing calendar (not the
     * parameter one). The name will be combined name1+"/"+calendar.getName().
     * 
     * @param calendar,
     *            return the same DateCalculator if calender is null or the
     *            original calendar (but why would you want to do that?)
     * @throws IllegalArgumentException
     *             if both calendars have different types of HolidayHandlers or
     *             WorkingWeek;
     */
    public DateCalculator combine(final DateCalculator calendar) {
        if (calendar == null || calendar == this) {
            return this;
        }

        if (holidayHandler == null && calendar.getHolidayHandlerType() != null || holidayHandler != null
                && !holidayHandler.getType().equals(calendar.getHolidayHandlerType())) {
            throw new IllegalArgumentException("Combined Calendars cannot have different handler types");
        }

        final Set<Date> newSet = new HashSet<Date>();
        if (nonWorkingDays != null) {
            newSet.addAll(nonWorkingDays);
        }
        if (calendar.getNonWorkingDays() != null) {
            newSet.addAll(calendar.getNonWorkingDays());
        }

        final DateCalculator cal = new BaseDateCalculator(getName() + "/" + calendar.getName(), getStartDate(), newSet,
                holidayHandler);

        return cal;
    }

    /**
     * Calculates IMMDates between a start and end dates
     * the 3rd wednesday of Mar/Jun/Sep/Dec
     * when a lot of derivative contracts expire
     * @return a List of Dates
     */
    public List<Date> getIMMDates(final Date start, final Date end) {
        
        List<Date> dates = new ArrayList<Date>();
        Date date = start;
        while (true) {
            date = getNextIMMDate(true, date);
            if (!date.after(end)) {
                dates.add(date);
            } else {
                break;
            }
        }
        
         return dates;
    }

    public Date getNextIMMDate() {
        return getNextIMMDate(true, currentDate);
    }

    public Date getPreviousIMMDate() {
        return getNextIMMDate(false, currentDate);
    }

    private Date getNextIMMDate(final boolean forward, final Date startDate) {
        
        Calendar cal = Utils.getCal(startDate);

        if (isIMMMonth(cal)) {
            moveToIMMDay(cal);
            //TODO simplify this if condition
            if ((forward && cal.getTime().after(startDate)) ||
                    (!forward && cal.getTime().before(startDate))) {
                return cal.getTime();
            }
        }
        
        int delta = (forward ? 1 : -1);
        do {
            cal.add(Calendar.MONTH, delta);
        } while (!isIMMMonth(cal));
        
        moveToIMMDay(cal);
        return cal.getTime();
    }

    private boolean isIMMMonth(final Calendar cal) {
        int month = cal.get(Calendar.MONTH);
        return (month == Calendar.MARCH || month == Calendar.JUNE || 
                month == Calendar.SEPTEMBER || month == Calendar.DECEMBER);
    }
    
    /**
     * Assumes that the month is correct, get the day for the 3rd wednesday.
     * 
     * @param first
     * @return
     */
    private void moveToIMMDay(final Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        
        // go to 1st wed
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek < Calendar.WEDNESDAY) {
            cal.add(Calendar.DAY_OF_MONTH, Calendar.WEDNESDAY - dayOfWeek);
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            // do nothing
        } else {
            cal.add(Calendar.DAY_OF_MONTH, (Calendar.WEDNESDAY + 7) - dayOfWeek);
        }
        
        // go to 3rd wednesday - i.e. move 2 weeks forward
        cal.add(Calendar.DAY_OF_MONTH, 7 * 2);
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
    public DateCalculator moveByTenor(final Tenor tenor) {
        if (tenor == null) {
            throw new IllegalArgumentException("Tenor cannot be null");
        }
        
        switch (tenor.getCode()) {
        case DAY:
            return addDays(tenor.getUnits());
        case WEEK:
            return addDays(tenor.getUnits() * DAYS_IN_WEEK);
        case IMM:
            setCurrentBusinessDate(getNextIMMDate());
            return this;
        default:
            throw new UnsupportedOperationException("Sorry not yet...");
        }
        
    }
}
