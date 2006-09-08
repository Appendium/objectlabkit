/*
 * $Id: DateCalculatorFactory.java 125 2006-09-07 17:24:20Z benoitx $
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
 * Factory will create new instances of calculators, these are lightweight, each
 * thread should use the factory as a given calculator should NOT be shared
 * across thread (unless you know what you're doing) as the startDate, current
 * date and working week would be shared. Once created, the set of holidays will
 * NOT change even if a new set is registered; one needs to get a new
 * DateCalculator to get the new set.
 * 
 * @param E
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy: benoitx $
 * @version $Revision: 125 $ $Date: 2006-09-07 19:24:20 +0200 (Thu, 07 Sep 2006) $
 */
public interface KitCalculatorsFactory<E> {

    /**
     * Create a new DateCalculator for a given name and type of handling.
     * 
     * @param name
     *            calendar name (holidays set interested in). If there is set of
     *            holidays with that name, it will return a DateCalculator with
     *            an empty holiday set (will work on Weekend only).
     * @param type
     *            typically one of the value of HolidayHandlerType or null.
     * @return a new DateCalculator
     * @exception IllegalArgumentException
     *                if the type is not null or a valid value.
     */
    DateCalculator<E> getDateCalculator(final String name, final String holidayHandlerType);

    /**
     * Use this method to register a set of holidays for a given calendar.
     * 
     * @param name
     *            the calendar name to register these holidays under.
     * @param holidays
     *            the set of holidays (non-working days).
     */
    void registerHolidays(final String name, Set<E> holidays);

    /**
     * Create a new PeriodCountCalculator.
     * 
     * @return a PeriodCountCalculator
     */
    PeriodCountCalculator<E> getPeriodCountCalculator();

    /**
     * Create a new IMMDateCalculator.
     * 
     * @return an IMMDateCalculator
     */
    IMMDateCalculator<E> getIMMDateCalculator();
}
