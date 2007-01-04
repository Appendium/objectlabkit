/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
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

import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * The default factory for getting Jdk <code>Date</code> based calculators.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class DateKitCalculatorsFactory extends AbstractKitCalculatorsFactory<Date> {

    private static final DateKitCalculatorsFactory DEFAULT = new DateKitCalculatorsFactory();

    private static final PeriodCountCalculator<Date> PCC = new DatePeriodCountCalculator();

    private static final DateIMMDateCalculator IMMDC = new DateIMMDateCalculator();

    public static DateKitCalculatorsFactory getDefaultInstance() {
        return DEFAULT;
    }

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getDateCalculator(java.lang.String,
     *      java.lang.String)
     */
    public DateCalculator<Date> getDateCalculator(final String name, final String holidayHandlerType) {
        final DateDateCalculator cal = new DateDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        if (holidayHandlerType == null) {
            return cal;
        } else if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateForwardHandler());
        } else if (HolidayHandlerType.BACKWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateBackwardHandler());
        } else if (HolidayHandlerType.MODIFIED_FOLLOWING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateModifiedFollowingHandler());
        } else if (HolidayHandlerType.MODIFIED_PRECEEDING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateModifiedPreceedingHandler());
        } else if (HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateForwardUnlessNegativeHandler());
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

/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 */
