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

    private static final int MAX_WEEKDAY_INDEX = 7;

    public static final JodaWorkingWeek DEFAULT = new JodaWorkingWeek();

    public JodaWorkingWeek() {
        super();
    }

    private JodaWorkingWeek(final byte workingDays) {
        super(workingDays);
    }

    public JodaWorkingWeek(final WorkingWeek ww) {
        this(ww.getWorkingDays());
    }

    public boolean isWorkingDay(final LocalDate date) {
        final int dayOfWeek = jodaToCalendarDayConstant(date.getDayOfWeek());
        return isWorkingDayFromCalendar(dayOfWeek);
    }

    /**
     * Return a new JodaWorkingWeek if the status for the given day has changed.
     * 
     * @param working
     *            true if working day
     * @param dayOfWeek
     *            e.g. DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, etc
     */
    public JodaWorkingWeek withWorkingDayFromDateTimeConstant(final boolean working, int dayOfWeek) {
        dayOfWeek = jodaToCalendarDayConstant(dayOfWeek);
        return new JodaWorkingWeek(super.withWorkingDayFromCalendar(working, dayOfWeek));
    }

    public boolean isWorkingDayFromDateTimeConstant(final int dayOfWeek) {
        return isWorkingDayFromCalendar(jodaToCalendarDayConstant(dayOfWeek));
    }

    public int jodaToCalendarDayConstant(int dayOfWeek) {
        dayOfWeek++;
        return (dayOfWeek <= MAX_WEEKDAY_INDEX ? dayOfWeek : dayOfWeek % MAX_WEEKDAY_INDEX);
    }
}
