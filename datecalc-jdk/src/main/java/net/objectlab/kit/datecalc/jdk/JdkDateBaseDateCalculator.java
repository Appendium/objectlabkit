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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
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
public class JdkDateBaseDateCalculator implements JdkDateCalculator {

    private JdkCalendarBaseDateCalculator delegate;

    public JdkDateBaseDateCalculator() {
        delegate = new JdkCalendarBaseDateCalculator();
    }

    public JdkDateBaseDateCalculator(final String name, final Date startDate, final Set<Date> nonWorkingDays,
            final HolidayHandler<Calendar> holidayHandler) {
        final Set<Calendar> nonWorkingCalendars = Utils.toCalendarSet(nonWorkingDays);

        delegate = new JdkCalendarBaseDateCalculator(name, Utils.getCal(startDate), nonWorkingCalendars, holidayHandler);
        delegate.setStartDate(Utils.getCal(startDate));
    }

    public Date getCurrentBusinessDate() {
        return delegate.getCurrentBusinessDate().getTime();
    }

    public Date getStartDate() {
        return delegate.getStartDate().getTime();
    }

    public boolean isNonWorkingDay(final Date date) {
        return delegate.isNonWorkingDay(Utils.getCal(date));
    }

    public boolean isWeekend(final Date date) {
        return delegate.isWeekend(Utils.getCal(date));
    }

    public JdkDateBaseDateCalculator moveByBusinessDays(final int businessDays) {
        delegate = delegate.moveByBusinessDays(businessDays);
        return this;
    }

    public JdkDateBaseDateCalculator moveByDays(final int days) {
        delegate = delegate.moveByDays(days);
        return this;
    }

    public JdkDateBaseDateCalculator moveByTenor(final Tenor tenor) {
        delegate = delegate.moveByTenor(tenor);
        return this;
    }

    public Date setCurrentBusinessDate(final Date date) {
        return delegate.setCurrentBusinessDate(Utils.getCal(date)).getTime();
    }

    public void setStartDate(final Date startDate) {
        delegate.setStartDate(Utils.getCal(startDate));
    }

    public String getName() {
        return delegate.getName();
    }

    public void setName(final String name) {
        delegate.setName(name);
    }

    public boolean isCurrentDateNonWorking() {
        return delegate.isCurrentDateNonWorking();
    }

    public void setNonWorkingDays(final Set<Date> holidays) {
        delegate.setNonWorkingDays(Utils.toCalendarSet(holidays));
    }

    public Set<Date> getNonWorkingDays() {
        return Utils.toDateSet(delegate.getNonWorkingDays());
    }

    public void setWorkingWeek(final WorkingWeek week) {
        delegate.setWorkingWeek(week);
    }

    public String getHolidayHandlerType() {
        return delegate.getHolidayHandlerType();
    }

    public void setHolidayHandler(final HolidayHandler<Calendar> holidayHandler) {
        delegate.setHolidayHandler(holidayHandler);
    }

    public JdkDateBaseDateCalculator combine(final DateCalculator<Date> calculator) {
        if (calculator == null || calculator == this) {
            return this;
        }

        if (delegate.getHolidayHandlerType() == null && calculator.getHolidayHandlerType() != null
                || delegate.getHolidayHandlerType() != null
                && !delegate.getHolidayHandlerType().equals(calculator.getHolidayHandlerType())) {
            throw new IllegalArgumentException("Combined Calendars cannot have different handler types");
        }

        final Set<Date> newSet = new HashSet<Date>();
        newSet.addAll(Utils.toDateSet(delegate.getNonWorkingDays()));
        newSet.addAll(calculator.getNonWorkingDays());
        return new JdkDateBaseDateCalculator(delegate.getName() + "/" + calculator.getName(), getStartDate(), newSet, delegate
                .getHolidayHandler());
    }
}
