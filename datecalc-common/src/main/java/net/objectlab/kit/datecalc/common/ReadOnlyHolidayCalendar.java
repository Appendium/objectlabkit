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
 * $Id: HolidayHandler.java 200 2006-10-10 20:15:58Z benoitx $
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

import java.io.Serializable;
import java.util.Set;

/**
 * A Holiday Calendar not only defines a set of holiday dates but an early and
 * late boundary for these dates, e.g. putting the holidays for 2006 in a set
 * with limits of 1 Jan 2006 and 31 Dec 2006 means that 2006 is covered, not
 * that 31 Dec is a holiday itself.
 *
 * @author Benoit Xhenseval
 * @since 1.4.0
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 *
 */
public interface ReadOnlyHolidayCalendar<E> extends Serializable {
    /**
     * Returns an immutable set of holidays.
     *
     * @return an immutable copy of the holiday set.
     */
    Set<E> getHolidays();

    /**
     * Returns the earliest date covered by this HolidayCalendar.
     * @return E the earliest date covered by this holiday calendar.
     */
    E getEarlyBoundary();

    /**
     * Returns the latest date covered by this HolidayCalendar.
     * @return E the latest date covered by this holiday calendar.
     */
    E getLateBoundary();
}

/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 *
 * Based in London, we are world leaders in the design and development of
 * bespoke applications for the securities financing markets.
 *
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
 *
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *
 *                      www.ObjectLab.co.uk
 */
