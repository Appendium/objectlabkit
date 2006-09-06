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
package net.objectlab.kit.datecalc.common;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO javadoc
 * 
 * @author marchi
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public abstract class AbstractDateCalculatorFactory<E> implements DateCalculatorFactory<E> {

    protected final ConcurrentMap<String, Set<E>> holidays = new ConcurrentHashMap<String, Set<E>>();

//    public abstract DateCalculator<E> getDateCalculator(String name, String holidayHandlerType);

//    public abstract PeriodCountCalculator<E> getPeriodCountCalculator();

    /**
     * Use this method to register a set of holidays for a given calendar, it
     * will replace any existing set. It won't update any existing
     * DateCalculator as these should not be amended whilst in existence (we
     * could otherwise get inconsistent results).
     * 
     * @param name
     *            the calendar name to register these holidays under.
     * @param holidays
     *            the set of holidays (non-working days).
     */
    public void registerHolidays(final String name, final Set<E> holidaysSet) {
        this.holidays.put(name, holidaysSet);
    }
}
