/*
 * $Id: org.eclipse.jdt.ui.prefs 138 2006-09-10 12:29:15Z marchy $
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

import java.util.Set;

/**
 * This is an immutable holiday calendar, once given to a DateCalculator, a HolidayCalendar cannot be
 * modified, it will throw {@link UnsupportedOperationException}.
 *
 * @author Benoit Xhenseval
 * @since 1.1.0
 */
public class ImmutableHolidayCalendar<E> implements HolidayCalendar<E> {
    private static final long serialVersionUID = 1287613980146071460L;

    private final HolidayCalendar<E> delegate;

    public ImmutableHolidayCalendar(final HolidayCalendar<E> delegate) {
        super();
        this.delegate = delegate;
    }

    /**
     * @return the early (start) boundary of the holiday range
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getEarlyBoundary()
     */
    public E getEarlyBoundary() {
        return delegate.getEarlyBoundary();
    }

    /**
     * @return the set of holidays
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getHolidays()
     */
    public Set<E> getHolidays() {
        return delegate.getHolidays();
    }

    /**
     * @return the late (end) boundary of the holiday range
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#getLateBoundary()
     */
    public E getLateBoundary() {
        return delegate.getLateBoundary();
    }

    /**
     * @param earlyBoundary
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setEarlyBoundary(java.lang.Object)
     * @throws UnsupportedOperationException You cannot modify the early boundary, you need to use a new HolidayCalendar.
     */
    public HolidayCalendar<E> setEarlyBoundary(final E earlyBoundary) {
        throw new UnsupportedOperationException("You cannot modify the early boundary, you need to use a new HolidayCalendar.");
    }

    /**
     * @param holidays
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setHolidays(java.util.Set)
     * @throws UnsupportedOperationException You cannot modify the holidays, you need to use a new HolidayCalendar.;
     */
    public HolidayCalendar<E> setHolidays(final Set<E> holidays) {
        throw new UnsupportedOperationException("You cannot modify the holidays, you need to use a new HolidayCalendar.");
    }

    /**
     * @param lateBoundary
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#setLateBoundary(java.lang.Object)
     * @throws UnsupportedOperationException You cannot modify the late boundary, you need to use a new HolidayCalendar.
     */
    public HolidayCalendar<E> setLateBoundary(final E lateBoundary) {
        throw new UnsupportedOperationException("You cannot modify the late boundary, you need to use a new HolidayCalendar.");
    }

    /**
     * @param date
     * @return
     * @see net.objectlab.kit.datecalc.common.HolidayCalendar#isHoliday(java.lang.Object)
     */
    public boolean isHoliday(final E date) {
        return delegate.isHoliday(date);
    }
}
