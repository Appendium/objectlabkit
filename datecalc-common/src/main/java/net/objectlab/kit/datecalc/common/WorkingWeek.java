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
package net.objectlab.kit.datecalc.common;

import java.util.Calendar;
import java.util.Date;

/**
 * Immutable Working Week, default is Mon->Friday.
 * 
 * @author Benoit Xhenseval
 */
public class WorkingWeek {
    private static final int DEFAULT_WEEK = 31;

    private static final byte[] CALENDAR_DAYS = new byte[] { 64, 1, 2, 4, 8, 16, 32 };

    public static final WorkingWeek DEFAULT = new WorkingWeek();

    /**
     * Default Working Week Monday -> Friday.
     */
    public WorkingWeek() {
        this((byte) DEFAULT_WEEK);
    }

    private WorkingWeek(final byte workingDays) {
        this.workingDays = workingDays;
    }

    /**
     * working days: 1 Monday, 2 Tuesday, 4 Wednesday, 8 Thursday, 16 Friday, 32
     * Saturday, 64 Sunday So Monday-Friday= 1+2+4+8+16 = 31
     */
    private byte workingDays = DEFAULT_WEEK;

    public boolean isWorkingDayFromCalendar(final int dayOfWeek) {
        final int day = adjustDay(dayOfWeek);
        return (CALENDAR_DAYS[day] & workingDays) != 0;
    }

    public boolean isWorkingDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return isWorkingDayFromCalendar(cal.get(Calendar.DAY_OF_WEEK));
    }

    private int adjustDay(final int dayOfWeek) {
        return dayOfWeek - 1;
    }

    /**
     * If the value for the given day has changed, return a NEW WorkingWeek.
     * 
     * @param working
     *            true if working day
     * @param dayOfWeek
     *            e.g. Calendar.MONDAY, Calendar.TUESDAY, etc
     * @return TODO
     */
    public WorkingWeek withWorkingDayFromCalendar(final boolean working, final int dayOfWeek) {
        final int day = adjustDay(dayOfWeek);
        WorkingWeek ret = this;
        if (working && (!isWorkingDayFromCalendar(dayOfWeek))) {
            ret = new WorkingWeek((byte) (workingDays + CALENDAR_DAYS[day]));
        } else if (!working && isWorkingDayFromCalendar(dayOfWeek)) {
            ret = new WorkingWeek((byte) (workingDays - CALENDAR_DAYS[day]));
        }
        return ret;
    }
}
