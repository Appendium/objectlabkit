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

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * The default factory for getting Jdk <code>Calendar</code> based
 * calculators.
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class CalendarKitCalculatorsFactory extends AbstractKitCalculatorsFactory<Calendar> {

    private static final CalendarKitCalculatorsFactory DEFAULT = new CalendarKitCalculatorsFactory();

    private static final PeriodCountCalculator<Calendar> PCC = new CalendarPeriodCountCalculator();

    private static final CalendarIMMDateCalculator IMMDC = new CalendarIMMDateCalculator();

    public static CalendarKitCalculatorsFactory getDefaultInstance() {
        return DEFAULT;
    }

    /**
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getDateCalculator(java.lang.String,
     *      java.lang.String)
     */
    public DateCalculator<Calendar> getDateCalculator(final String name, final String holidayHandlerType) {
        final CalendarDateCalculator cal = new CalendarDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        if (holidayHandlerType == null) {
            return cal;
        } else if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new CalendarForwardHandler());
        } else if (HolidayHandlerType.BACKWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new CalendarBackwardHandler());
        } else if (HolidayHandlerType.MODIFIED_FOLLLOWING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new CalendarModifiedFollowingHandler());
        } else if (HolidayHandlerType.MODIFIED_PRECEEDING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new CalendarModifiedPreceedingHandler());
        } else {
            throw new IllegalArgumentException("Unsupported HolidayHandler: " + holidayHandlerType);
        }

        return cal;
    }

    /**
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getPeriodCountCalculator()
     */
    public PeriodCountCalculator<Calendar> getPeriodCountCalculator() {
        return PCC;
    }

    /**
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getIMMDateCalculator()
     */
    public IMMDateCalculator<Calendar> getIMMDateCalculator() {
        return IMMDC;
    }

}
