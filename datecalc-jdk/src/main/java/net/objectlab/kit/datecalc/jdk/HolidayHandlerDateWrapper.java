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

/**
 * A Wrapper to handle any HolidayHandler<Date> types via a HolidayHandler<Calendar> delegate
 *
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 *
 */
public class HolidayHandlerDateWrapper implements HolidayHandler<Date> {

    HolidayHandler<Calendar> delegate;
    DateCalculator<Calendar> calculator;
    
    public HolidayHandlerDateWrapper(HolidayHandler<Calendar> holidayHandler, DateCalculator<Calendar> dateCalulator) {
        delegate = holidayHandler;
        calculator = dateCalulator;
    }
    
    public Date moveCurrentDate(DateCalculator<Date> calendar) {
        return delegate.moveCurrentDate(calculator).getTime();
    }

    public String getType() {
        return delegate.getType();
    }
    
}
