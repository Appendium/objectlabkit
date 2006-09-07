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
import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.Utils;

/**
 * A Wrapper to handle any HolidayHandler<Date> types via a HolidayHandler<Calendar>
 * delegate
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class HolidayHandlerDateWrapper implements HolidayHandler<Calendar> {

    private HolidayHandler<Date> delegate;

    private DateCalculator<Date> calculator;

    public HolidayHandlerDateWrapper(final HolidayHandler<Date> holidayHandler, final DateCalculator<Date> dateCalculator) {
        delegate = holidayHandler;
        calculator = dateCalculator;
    }

    public Calendar moveCurrentDate(final DateCalculator<Calendar> calendar) {
        Calendar ret = calendar.getCurrentBusinessDate();
        if (delegate != null) {
            final Date day = delegate.moveCurrentDate(calculator);
            if (day != null) {
                ret = Utils.getCal(day);
            }
        }
        return ret;
    }

    public String getType() {
        return delegate.getType();
    }

}
