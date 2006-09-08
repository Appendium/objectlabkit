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

import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

import org.joda.time.YearMonthDay;

public class YearMonthDayKitCalculatorsFactory extends AbstractKitCalculatorsFactory<YearMonthDay> {

    private static final KitCalculatorsFactory<YearMonthDay> DEFAULT = new YearMonthDayKitCalculatorsFactory();

    private static final PeriodCountCalculator<YearMonthDay> PCC = new YearMonthDayPeriodCountCalculator();

    private static final IMMDateCalculator<YearMonthDay> IMMDC = new YearMonthDayIMMDateCalculator();

    public static KitCalculatorsFactory<YearMonthDay> getDefaultInstance() {
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
    public DateCalculator<YearMonthDay> getDateCalculator(final String name, final String holidayHandlerType) {
        final YearMonthDayDateCalculator cal = new YearMonthDayDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayForwardHandler());
        } else if (HolidayHandlerType.BACKWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayBackwardHandler());
        } else if (HolidayHandlerType.MODIFIED_FOLLLOWING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayModifiedFollowingHandler());
        } else if (HolidayHandlerType.MODIFIED_PRECEEDING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayModifiedPreceedingHandler());
        } else if (holidayHandlerType != null) {
            throw new IllegalArgumentException("Unsupported HolidayHandler: " + holidayHandlerType);
        }
        return cal;
    }

    public PeriodCountCalculator<YearMonthDay> getPeriodCountCalculator() {
        return PCC;
    }

    public IMMDateCalculator<YearMonthDay> getIMMDateCalculator() {
        return IMMDC;
    }
}
