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
package net.objectlab.kit.datecalc.jdk8;

import java.time.DayOfWeek;
import java.time.LocalDate;

import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * Immutable class representing a WorkingWeek for the Joda implementation.
 *
 * @author Benoit Xhenseval
 */
public class Jdk8WorkingWeek extends WorkingWeek {

    private static final int MAX_WEEKDAY_INDEX = 7;

    public static final Jdk8WorkingWeek DEFAULT = new Jdk8WorkingWeek();

    public Jdk8WorkingWeek() {
        super();
    }

    protected Jdk8WorkingWeek(final byte workingDays) {
        super(workingDays);
    }

    public Jdk8WorkingWeek(final WorkingWeek ww) {
        this(ww.getWorkingDays());
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public boolean isWorkingDay(final LocalDate date) {
        final int dayOfWeek = jdk8ToCalendarDayConstant(date.getDayOfWeek());
        return isWorkingDayFromCalendar(dayOfWeek);
    }

    /**
     * Return a new JodaWorkingWeek if the status for the given day has changed.
     *
     * @param working
     *            true if working day
     * @param givenDayOfWeek
     *            e.g. DateTimeConstants.MONDAY, DateTimeConstants.TUESDAY, etc
     */
    public Jdk8WorkingWeek withWorkingDayFromDateTimeConstant(final boolean working, final DayOfWeek givenDayOfWeek) {
        final int dayOfWeek = jdk8ToCalendarDayConstant(givenDayOfWeek);
        return new Jdk8WorkingWeek(super.withWorkingDayFromCalendar(working, dayOfWeek));
    }

    public boolean isWorkingDayFromDateTimeConstant(final DayOfWeek dayOfWeek) {
        return isWorkingDayFromCalendar(jdk8ToCalendarDayConstant(dayOfWeek));
    }

    public static int jdk8ToCalendarDayConstant(final DayOfWeek givenDayOfWeek) {
        final int dayOfWeek = givenDayOfWeek.getValue() + 1;
        return dayOfWeek <= MAX_WEEKDAY_INDEX ? dayOfWeek : dayOfWeek % MAX_WEEKDAY_INDEX;
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
