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

import java.util.Calendar;
import java.util.Collections;
import java.util.Set;

import net.objectlab.kit.datecalc.common.AbstractDateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.Tenor;
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
public class JdkCalendarBaseDateCalculator extends AbstractDateCalculator<Calendar>  {

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
    protected DateCalculator<Calendar> createNewCalculator(final String name, final Calendar startDate, final Set<Calendar> holidays,
            final HolidayHandler<Calendar> handler) {
        return new JdkCalendarBaseDateCalculator(name, startDate, holidays, handler);
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
