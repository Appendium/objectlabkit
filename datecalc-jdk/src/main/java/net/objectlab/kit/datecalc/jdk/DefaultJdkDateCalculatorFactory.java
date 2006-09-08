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
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * TODO add javadoc
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class DefaultJdkDateCalculatorFactory extends AbstractDateCalculatorFactory<Date> {

    private static final DefaultJdkDateCalculatorFactory DEFAULT = new DefaultJdkDateCalculatorFactory();

    private static final PeriodCountCalculator<Date> PCC = new DatePeriodCountCalculator();

    private static final JdkDateIMMDateCalculator IMMDC = new JdkDateIMMDateCalculator();

    public static DefaultJdkDateCalculatorFactory getDefaultInstance() {
        return DEFAULT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getDateCalculator(java.lang.String,
     *      java.lang.String)
     */
    public DateCalculator<Date> getDateCalculator(final String name, final String holidayHandlerType) {
        final JdkDateBaseDateCalculator cal = new JdkDateBaseDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        if (holidayHandlerType == null) {
            return cal;
        } else if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateForwardHandler());
        } else if (HolidayHandlerType.BACKWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateBackwardHandler());
        } else if (HolidayHandlerType.MODIFIED_FOLLLOWING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateModifiedFollowingHandler());
        } else if (HolidayHandlerType.MODIFIED_PRECEEDING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateModifiedPreceedingHandler());
        } else {
            throw new IllegalArgumentException("Unsupported HolidayHandler: " + holidayHandlerType);
        }

        return cal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getPeriodCountCalculator()
     */
    public PeriodCountCalculator<Date> getPeriodCountCalculator() {
        return PCC;
    }

    public IMMDateCalculator<Date> getIMMDateCalculator() {
        return IMMDC;
    }

}
