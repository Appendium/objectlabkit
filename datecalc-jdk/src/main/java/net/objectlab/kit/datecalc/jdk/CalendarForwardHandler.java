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

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

/**
 * A Jdk <code>Calendar</code> implementation of the {@link HolidayHandler}, for the 
 * <strong>Forward</strong> algorithm.
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class CalendarForwardHandler implements HolidayHandler<Calendar> {

    public Calendar moveCurrentDate(final DateCalculator<Calendar> calendar) {
        return move(calendar, 1);
    }

    protected Calendar move(final DateCalculator<Calendar> calculator, final int step) {
        final Calendar cal = (Calendar) calculator.getCurrentBusinessDate().clone();

        while (calculator.isNonWorkingDay(cal)) {
            cal.add(Calendar.DAY_OF_MONTH, step);
        }

        return cal;
    }

    public String getType() {
        return HolidayHandlerType.FORWARD;
    }

}
