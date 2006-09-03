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
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

/**
 * TODO add javadoc 
 *
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 *
 */
public class DefaultJdkDateCalculatorFactory extends AbstractDateCalculatorFactory<Date> 
        implements JdkDateCalculatorFactory {
    
    private static final JdkDateCalculatorFactory DEFAULT = new DefaultJdkDateCalculatorFactory();

    private static final JdkDatePeriodCountCalculator PCC = new DatePeriodCountCalculator();

    public static JdkDateCalculatorFactory getDefaultInstance() {
        return DEFAULT;
    }

    /* (non-Javadoc)
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getDateCalculator(java.lang.String, java.lang.String)
     */
    public JdkDateCalculator getDateCalculator(final String name, final String holidayHandlerType) {
        final JdkDateBaseDateCalculator cal = new JdkDateBaseDateCalculator();
        cal.setName(name);
        if (holidays.containsKey(name)) {
            cal.setNonWorkingDays(holidays.get(name));
        }

        if (holidayHandlerType == null) {
            return cal;
        } else if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new ForwardHandler());
        } else if (HolidayHandlerType.BACKWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new BackwardHandler());
        } else if (HolidayHandlerType.MODIFIED_FOLLLOWING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new ModifiedFollowingHandler());
        } else if (HolidayHandlerType.MODIFIED_PRECEEDING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new ModifiedPreceedingHandler());
        } else {
            throw new UnsupportedOperationException("Unsupported HolidayHandler: " + holidayHandlerType);
        }

        return cal;
    }

    /* (non-Javadoc)
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getPeriodCountCalculator()
     */
    public JdkDatePeriodCountCalculator getPeriodCountCalculator() {
        return PCC;
    }

}
