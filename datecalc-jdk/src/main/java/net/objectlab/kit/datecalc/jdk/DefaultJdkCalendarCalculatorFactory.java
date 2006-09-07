/*
 * $Id: DefaultJdkDateCalculatorFactory.java 96 2006-09-04 15:01:20Z benoitx $
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

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorFactory;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;

/**
 * TODO add javadoc
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy: benoitx $
 * @version $Revision: 96 $ $Date: 2006-09-04 16:01:20 +0100 (Mon, 04 Sep 2006) $
 * 
 */
public class DefaultJdkCalendarCalculatorFactory extends AbstractDateCalculatorFactory<Calendar> {

    private static final DefaultJdkCalendarCalculatorFactory DEFAULT = new DefaultJdkCalendarCalculatorFactory();

    private static final CalendarPeriodCountCalculator PCC = new CalendarPeriodCountCalculator();

    private static final JdkCalendarIMMDateCalculator IMMDC = new JdkCalendarIMMDateCalculator();

    public static DefaultJdkCalendarCalculatorFactory getDefaultInstance() {
        return DEFAULT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getDateCalculator(java.lang.String,
     *      java.lang.String)
     */
    public DateCalculator<Calendar> getDateCalculator(final String name, final String holidayHandlerType) {
        final JdkCalendarBaseDateCalculator cal = new JdkCalendarBaseDateCalculator();
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

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getPeriodCountCalculator()
     */
    public CalendarPeriodCountCalculator getPeriodCountCalculator() {
        return PCC;
    }

    public IMMDateCalculator<Calendar> getIMMDateCalculator() {
        return IMMDC;
    }

}
