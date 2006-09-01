/*
 * $Id: org.eclipse.jdt.ui.prefs 59 2006-08-26 09:06:39Z marchy $
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
package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

/**
 * 
 * @author xhensevb
 * @author $LastChangedBy: marchy $
 * @version $Revision: 59 $ $Date: 2006-08-26 11:06:39 +0200 (Sat, 26 Aug 2006) $
 * 
 */
public class HolidayHandlerYearMonthDayWrapper implements HolidayHandler<LocalDate> {

    private HolidayHandler<YearMonthDay> delegate;

    private DateCalculator<YearMonthDay> calculator;

    public HolidayHandlerYearMonthDayWrapper(final HolidayHandler<YearMonthDay> delegate, final DateCalculator<YearMonthDay> calculator) {
        this.delegate = delegate;
        this.calculator = calculator;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayHandler#getType()
     */
    public String getType() {
        return delegate.getType();
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.common.HolidayHandler#moveCurrentDate(net.objectlab.kit.datecalc.common.DateCalculator)
     */
    public LocalDate moveCurrentDate(final DateCalculator<LocalDate> calendar) {
        LocalDate ret = calendar.getCurrentBusinessDate();
        if (delegate != null) {
            final YearMonthDay day = delegate.moveCurrentDate(calculator);
            if (day != null) {
                ret = day.toLocalDate();
            }
        }
        return ret;
    }

}
