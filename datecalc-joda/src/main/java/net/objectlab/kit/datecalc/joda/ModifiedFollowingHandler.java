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

import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.LocalDate;

/**
 * A modified following handler will move the date forward if it falls on a non
 * working day BUT, if the new date falls into another month, it will revert to
 * moving backward until it finds a working day.
 * 
 * @author Benoit Xhenseval
 */
public class ModifiedFollowingHandler implements HolidayHandler {

    public LocalDate moveCurrentDate(final BaseDateCalculator calendar) {
        LocalDate date = calendar.getCurrentDate();
        final int month = date.getMonthOfYear();
        int step = 1;
        while (calendar.isNonWorkingDay(date)) {
            date = date.minusDays(step);
            if (date.getMonthOfYear() != month) {
                // flick to backward
                step = -1;
                date = date.minusDays(step);
            }
        }
        return date;
    }

    public String getType() {
        return HolidayHandlerType.MODIFIED_FOLLLOWING;
    }

}
