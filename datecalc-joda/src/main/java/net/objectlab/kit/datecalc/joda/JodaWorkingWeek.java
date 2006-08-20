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
package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.LocalDate;

/**
 * Immutable class representing a WorkingWeek.
 * 
 * @author Benoit Xhenseval
 */
public class JodaWorkingWeek extends WorkingWeek {
    private static final int DEFAULT_WEEK = 31;

    private static final byte[] DAYS = new byte[] { 1, 2, 4, 8, 16, 32, 64 };

    public static final JodaWorkingWeek DEFAULT = new JodaWorkingWeek();

    /**
     * working days: 1 Monday 2 Tuesday 4 Wednesday 8 Thursday 16 Friday 32
     * Saturday 64 Sunday So Monday-Friday= 1+2+4+8+16 = 31
     */
    private byte workingDays = DEFAULT_WEEK;

    public JodaWorkingWeek() {
        this((byte) DEFAULT_WEEK);
    }

    private JodaWorkingWeek(final byte workingDays) {
        this.workingDays = workingDays;
    }

    public boolean isWorkingDayFromDateTimeConstant(final int dayOfWeek) {
        return (DAYS[dayOfWeek - 1] & workingDays) != 0;
    }

    public boolean isWorkingDay(final LocalDate date) {
        return isWorkingDayFromDateTimeConstant(date.getDayOfWeek());
    }

    /**
     * Return a new JodaWorkingWeek if the status for the given day has changed.
     * 
     * @param working
     *            true if working day
     * @param dayOfWeek
     *            e.g. DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, etc
     */
    public JodaWorkingWeek withWorkingDayFromDateTimeConstant(final boolean working, final int dayOfWeek) {
        final int day = dayOfWeek - 1;
        JodaWorkingWeek ret = this;
        if (working && (!isWorkingDayFromDateTimeConstant(dayOfWeek))) {
            ret = new JodaWorkingWeek((byte) (workingDays + DAYS[day]));
        } else if (!working && isWorkingDayFromDateTimeConstant(dayOfWeek)) {
            ret = new JodaWorkingWeek((byte) (workingDays - DAYS[day]));
        }
        return ret;
    }
}
