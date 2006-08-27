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
 */package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

/**
 * TODO javadoc
 *
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 *
 */
public class ModifiedFollowingHandler implements HolidayHandler<Date> {

    public Date moveCurrentDate(DateCalculator<Date> calendar) {
        return move(calendar, 1);
    }

    protected Date move(DateCalculator<Date> calendar, int step) {

        final Calendar cal = Utils.getCal(calendar.getCurrentBusinessDate());

        int month = cal.get(Calendar.MONTH);
        
        while (calendar.isNonWorkingDay(cal.getTime())) {
            cal.add(Calendar.DAY_OF_MONTH, step);
            if (month != cal.get(Calendar.MONTH)) {
                step *= -1;
            }
        }

        return cal.getTime();
    }
    
    public String getType() {
        return HolidayHandlerType.MODIFIED_FOLLLOWING;
    }

}
