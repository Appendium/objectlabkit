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
package net.objectlab.kit.datecalc.jdk;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.IMMPeriod;
import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.Utils;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 * 
 * @author Marcin Jekot
 * @author $LastModifiedBy$
 * @version $Revision$ $Date$
 */
public class JdkCalendarBaseDateCalculator extends AbstractDateCalculator<Calendar> implements JdkCalendarDateCalculator {

    private WorkingWeek workingWeek = WorkingWeek.DEFAULT;

    @SuppressWarnings("unchecked")
    public JdkCalendarBaseDateCalculator() {
        this(null, null, Collections.EMPTY_SET, null);
    }

    public JdkCalendarBaseDateCalculator(final String name, final Calendar startDate, final Set<Calendar> nonWorkingDays,
            final HolidayHandler<Calendar> holidayHandler) {
        super(name, nonWorkingDays, holidayHandler);
        setStartDate(startDate);
    }

    public void setWorkingWeek(final WorkingWeek week) {
        workingWeek = week;
    }

    /**
     * is the date a non-working day according to the WorkingWeek?
     */
    public boolean isWeekend(final Calendar date) {
        assert workingWeek != null;
        return !workingWeek.isWorkingDay(date);
    }

    public JdkCalendarBaseDateCalculator moveByDays(final int days) {
        if (getCurrentBusinessDate() == null) {
            initialise();
        }
        getCurrentBusinessDate().add(Calendar.DAY_OF_MONTH, days);
        
        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }
        
        return this;
    }

    private void initialise() {
        if (getStartDate() == null) {
            setStartDate(Calendar.getInstance());
        } else if (getCurrentBusinessDate() == null) {
            setCurrentBusinessDate(Calendar.getInstance());
        }
    }

    @Override
    protected JdkCalendarDateCalculator createNewCalcultaor(final String name, final Calendar startDate, final Set<Calendar> holidays,
            final HolidayHandler<Calendar> handler) {
        return new JdkCalendarBaseDateCalculator(name, startDate, holidays, handler);
    }

    /**
     * Returns a list of IMM dates between 2 dates, it will exclude the start
     * date if it is an IMM date but would include the end date if it is an IMM.
     * 
     * @param start
     *            start of the interval, excluded
     * @param end
     *            end of the interval, may be included.
     * @param period
     *            specify when the "next" IMM is, if quarterly then it is the
     *            conventional algorithm.
     * @return list of IMM dates
     */
    public List<Calendar> getIMMDates(final Calendar start, final Calendar end, final IMMPeriod period) {

        final List<Calendar> dates = new ArrayList<Calendar>();
        Calendar cal = (Calendar) start.clone();
        while (true) {
            cal = getNextIMMDate(true, cal, period);
            if (!cal.after(end)) {
                dates.add(cal);
            } else {
                break;
            }
        }

        return dates;
    }

    @Override
    protected Calendar getNextIMMDate(final boolean forward, final Calendar startDate, final IMMPeriod period) {

        Calendar cal = (Calendar) startDate.clone();
        
        if (isIMMMonth(cal)) {
            moveToIMMDay(cal);
            // TODO simplify this if condition
// if (forward ^ cal.getTime().before(startDate) ||
// cal.getTime().equals(startDate)) {
            if ((forward && cal.after(startDate)) || (!forward && cal.before(startDate))) {
                return cal;
            }
        }

        final int delta = (forward ? 1 : -1);
        do {
            cal.add(Calendar.MONTH, delta);
        } while (!isIMMMonth(cal));

        moveToIMMDay(cal);
        
        final int month = cal.get(Calendar.MONTH); 
        if ( (period == IMMPeriod.BI_ANNUALY_JUN_DEC 
                && (Calendar.MARCH == month || Calendar.SEPTEMBER==month) ) 
                || 
                ( period == IMMPeriod.BI_ANNUALY_MAR_SEP 
                        && (Calendar.JUNE == month || Calendar.DECEMBER==month) ) ) { 
                // need to move to the next one.
                cal = getNextIMMDate(forward, cal, period);
            }
        
        
        return cal;
    }

    private boolean isIMMMonth(final Calendar cal) {
        final int month = cal.get(Calendar.MONTH);
        
        switch (month) {
        case Calendar.MARCH:
        case Calendar.JUNE:
        case Calendar.SEPTEMBER:
        case Calendar.DECEMBER:
            return true;
        }
        
        return false;
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
        } else if (dayOfWeek > Calendar.WEDNESDAY){
            cal.add(Calendar.DAY_OF_MONTH, (Calendar.WEDNESDAY + 7) - dayOfWeek);
        }

        // go to 3rd wednesday - i.e. move 2 weeks forward
        cal.add(Calendar.DAY_OF_MONTH, 7 * 2);
    }

    public boolean isIMMDate(final Calendar date) {
        //TODO a slightly crude implementation - revisit
        Calendar cal = (Calendar)date.clone();
        moveToIMMDay(cal);
        return cal.equals(date);
    }
    
    @Override
    public JdkCalendarBaseDateCalculator combine(final DateCalculator<Calendar> calendar) {
        return (JdkCalendarBaseDateCalculator) super.combine(calendar);
    }

    @Override
    public JdkCalendarBaseDateCalculator moveByTenor(final Tenor tenor) {
        return (JdkCalendarBaseDateCalculator) super.moveByTenor(tenor);
    }

    @Override
    public JdkCalendarBaseDateCalculator moveByBusinessDays(final int businessDays) {
        return (JdkCalendarBaseDateCalculator) super.moveByBusinessDays(businessDays);
    }

}
