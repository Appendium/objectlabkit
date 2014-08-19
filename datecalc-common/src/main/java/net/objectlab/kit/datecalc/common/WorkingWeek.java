/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.
 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
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

import java.util.Calendar;
import java.util.Date;

/**
 * Immutable Working Week, default is Mon-&gt;Friday.
 *
 * @author Benoit Xhenseval
 *
 */
public class WorkingWeek {
    private static final byte MONDAY = 1;

    private static final byte TUESDAY = 2;

    private static final byte WEDNESDAY = 4;

    private static final byte THURSDAY = 8;

    private static final byte FRIDAY = 16;

    private static final byte SATURDAY = 32;

    private static final byte SUNDAY = 64;

    private static final byte DEFAULT_WORKING_DAYS = (byte) (MONDAY + TUESDAY + WEDNESDAY + THURSDAY + FRIDAY);

    private static final byte ARABIC_WORKING_DAYS = (byte) (MONDAY + TUESDAY + WEDNESDAY + THURSDAY + SUNDAY);

    private static final byte[] WORKING_WEEK_DAYS_OFFSET = new byte[] { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY };

    public static final WorkingWeek DEFAULT = new WorkingWeek();

    public static final WorkingWeek ARABIC_WEEK = new WorkingWeek(ARABIC_WORKING_DAYS);

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * working days: 1 Monday, 2 Tuesday, 4 Wednesday, 8 Thursday, 16 Friday, 32
     * Saturday, 64 Sunday So Monday-Friday= 1+2+4+8+16 = 31
     */
    private byte workingDays = DEFAULT_WORKING_DAYS;

    /**
     * Default Working Week Monday -&gt; Friday.
     */
    public WorkingWeek() {
        this(DEFAULT_WORKING_DAYS);
    }

    protected WorkingWeek(final byte workingDays) {
        this.workingDays = workingDays;
    }

    /**
     * Create a new calendar with the intersection of WORKING days.
     * e.g. if normal and arabic calendars are intersected, the week is 3 days: Fri-Sun.
     * @param ww
     * @return a new Working week
     * @since 1.4.0
     */
    public WorkingWeek intersection(final WorkingWeek ww) {
        final byte combined = (byte) (this.workingDays & ww.workingDays);
        return new WorkingWeek(combined);
    }

    public boolean isWorkingDayFromCalendar(final int dayOfWeek) {
        final int day = adjustDay(dayOfWeek);
        return (WORKING_WEEK_DAYS_OFFSET[day] & workingDays) != 0;
    }

    public boolean isWorkingDay(final Date date) {
        return isWorkingDay(Utils.getCal(date));
    }

    public boolean isWorkingDay(final Calendar cal) {
        return isWorkingDayFromCalendar(cal.get(Calendar.DAY_OF_WEEK));
    }

    protected int adjustDay(final int dayOfWeek) {
        return dayOfWeek - 1;
    }

    /**
     * If the value for the given day has changed, return a NEW WorkingWeek.
     *
     * @param working
     *            true if working day
     * @param dayOfWeek
     *            e.g. Calendar.MONDAY, Calendar.TUESDAY, etc
     * @return a new instance of a <code>WorkingWeek</code> with the working
     *         day set
     */
    public WorkingWeek withWorkingDayFromCalendar(final boolean working, final int dayOfWeek) {
        final int day = adjustDay(dayOfWeek);
        WorkingWeek ret = this;
        if (working && !isWorkingDayFromCalendar(dayOfWeek)) {
            ret = new WorkingWeek((byte) (workingDays + WORKING_WEEK_DAYS_OFFSET[day]));
        } else if (!working && isWorkingDayFromCalendar(dayOfWeek)) {
            ret = new WorkingWeek((byte) (workingDays - WORKING_WEEK_DAYS_OFFSET[day]));
        }
        return ret;
    }

    public byte getWorkingDays() {
        return workingDays;
    }
}

/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 *
 * Based in London, we are world leaders in the design and development
 * of bespoke applications for the securities financing markets.
 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 */
