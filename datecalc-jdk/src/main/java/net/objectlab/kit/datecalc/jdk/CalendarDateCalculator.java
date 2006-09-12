/*
 * $Id: JdkCalendarBaseDateCalculator.java 128 2006-09-08 13:14:46Z benoitx $
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
import net.objectlab.kit.datecalc.common.Utils;
import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * This class is used via the DateCalculator interface, it enables the handling
 * of different HolidayHandler, if no HolidayHandler is defined, the calendar
 * will NOT move a date, even if it falls on a holiday or weekend.
 * 
 * @author Marcin Jekot
 * @author $LastModifiedBy$
 * @version $Revision: 128 $ $Date: 2006-09-08 15:14:46 +0200 (Fri, 08 Sep 2006) $
 */
public class CalendarDateCalculator extends AbstractDateCalculator<Calendar> {

    private WorkingWeek workingWeek = WorkingWeek.DEFAULT;

    @SuppressWarnings("unchecked")
    public CalendarDateCalculator() {
        this(null, null, Collections.EMPTY_SET, null);
    }

    public CalendarDateCalculator(final String name, final Calendar startDate, final Set<Calendar> nonWorkingDays,
            final HolidayHandler<Calendar> holidayHandler) {
        super(name, nonWorkingDays, holidayHandler);
        Calendar date = startDate;
        if (date == null) {
            date = getToday();
        }
        setStartDate(date);
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

    public CalendarDateCalculator moveByDays(final int days) {
        getCurrentBusinessDate().add(Calendar.DAY_OF_MONTH, days);

        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }

        return this;
    }

    @Override
    public DateCalculator<Calendar> moveByMonths(final int months) {
         Calendar date = getCurrentBusinessDate();
         date.add(Calendar.MONTH,months);

         setCurrentBusinessDate(date);

        if (getHolidayHandler() != null) {
            setCurrentBusinessDate(getHolidayHandler().moveCurrentDate(this));
        }

        return this;
    }

    @Override
    protected DateCalculator<Calendar> createNewCalculator(final String name, final Calendar startDate,
            final Set<Calendar> holidays, final HolidayHandler<Calendar> handler) {
        return new CalendarDateCalculator(name, startDate, holidays, handler);
    }

    @Override
    protected Calendar getToday() {
        return Utils.blastTime(Calendar.getInstance());
    }
}
