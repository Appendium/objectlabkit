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

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorFactory;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * TODO add javadoc 
 *
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 *
 */
public class DefaultDateCalculatorFactory extends AbstractDateCalculatorFactory<Date> 
        implements DateCalculatorFactory<Date> {
    
    private static final DateCalculatorFactory<Date> DEFAULT = new DefaultDateCalculatorFactory();

    private static final PeriodCountCalculator<Date> PCC = new DefaultPeriodCountCalculator();

    public static DateCalculatorFactory<Date> getDefaultInstance() {
        return DEFAULT;
    }

    /**
     * Create a new DateCalculator for a given name and type of handling.
     * 
     * @param name
     *            calendar name (holidays set interested in). If there is set of
     *            holidays with that name, it will return a DateCalculator with
     *            an empty holiday set (will work on Weekend only).
     * @param type
     *            typically one of the value of HolidayHandlerType
     * @return a new DateCalculator
     */
    public DateCalculator<Date> getDateCalculator(final String name, final String holidayHandlerType) {
        final BaseDateCalculator cal = new BaseDateCalculator();
        cal.setName(name);
        if (holidays.containsKey(name)) {
            cal.setNonWorkingDays(holidays.get(name));
        }

        if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new ForwardHandler());
        } else {
            throw new UnsupportedOperationException("only forward supported");
        }

        return cal;

        // } else if (HolidayHandlerType.BACKWARD.equals(holidayHandlerType)) {
        // cal.setHolidayHandler(new BackwardHandler());
        // } else if
        // (HolidayHandlerType.MODIFIED_FOLLLOWING.equals(holidayHandlerType)) {
        // cal.setHolidayHandler(new ModifiedFollowingHandler());
        // } else if
        // (HolidayHandlerType.MODIFIED_PRECEEDING.equals(holidayHandlerType)) {
        // cal.setHolidayHandler(new ModifiedPreceedingHandler());
        // }
        // return cal;
    }

    public PeriodCountCalculator<Date> getPeriodCountCalculator() {
        return PCC;
    }

}
