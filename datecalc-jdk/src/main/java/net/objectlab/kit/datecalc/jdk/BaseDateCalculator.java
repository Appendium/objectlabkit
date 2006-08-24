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
import java.util.List;
import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 * 
 * @author Benoit Xhenseval
 */
public class BaseDateCalculator extends AbstractDateCalculator<Date> {

    private WorkingWeek workingWeek = WorkingWeek.DEFAULT;

    @SuppressWarnings("unchecked")
    public BaseDateCalculator() {
        this(null, null, Collections.EMPTY_SET, null);
    }

    public BaseDateCalculator(final String name, final Date startDate, final Set<Date> nonWorkingDays,
            final HolidayHandler<Date> holidayHandler) {
        super(name, nonWorkingDays, holidayHandler);
        setStartDate(startDate);
    }

    public void setWorkingWeek(final WorkingWeek week) {
        workingWeek = week;
    }

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final Date date) {
        assert workingWeek != null;
        return !workingWeek.isWorkingDay(date);
    }

    public DateCalculator<Date> moveByDays(final int days) {
        if (getCurrentBusinessDate() == null) {
            initialise();
        }

        final Calendar cal = Utils.getCal(getCurrentBusinessDate());
        cal.add(Calendar.DAY_OF_MONTH, days);
        setCurrentBusinessDate(cal.getTime());

        return this;
    }

    private void initialise() {
        if (getStartDate() == null) {
            setStartDate(new Date());
        } else if (getCurrentBusinessDate() == null) {
            setCurrentBusinessDate(new Date());
        }
    }

    @Override
    protected DateCalculator<Date> createNewCalcultaor(final String name, final Date startDate, final Set<Date> holidays,
            final HolidayHandler<Date> handler) {
        return new BaseDateCalculator(name, startDate, holidays, handler);
    }

    /**
     * Calculates IMMDates between a start and end dates the 3rd wednesday of
     * Mar/Jun/Sep/Dec when a lot of derivative contracts expire
     * 
     * @return a List of Dates
     */
    public List<Date> getIMMDates(final Date start, final Date end) {

        final List<Date> dates = new ArrayList<Date>();
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

    @Override
    protected Date getNextIMMDate(final boolean forward, final Date startDate) {

        final Calendar cal = Utils.getCal(startDate);

        if (isIMMMonth(cal)) {
            moveToIMMDay(cal);
            // TODO simplify this if condition
            if ((forward && cal.getTime().after(startDate)) || (!forward && cal.getTime().before(startDate))) {
                return cal.getTime();
            }
        }

        final int delta = (forward ? 1 : -1);
        do {
            cal.add(Calendar.MONTH, delta);
        } while (!isIMMMonth(cal));

        moveToIMMDay(cal);
        return cal.getTime();
    }

    private boolean isIMMMonth(final Calendar cal) {
        final int month = cal.get(Calendar.MONTH);
        return (month == Calendar.MARCH || month == Calendar.JUNE || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER);
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
        final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
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
}
