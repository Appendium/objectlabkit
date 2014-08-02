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

import static net.objectlab.kit.datecalc.common.HolidayHandlerType.BACKWARD;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.FORWARD;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_FOLLOWING;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_PRECEDING;

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

/**
 * The default factory for getting Jdk <code>Date</code> based calculators.
 * 
 * @author Benoit Xhenseval
 * 
 */
public class DateKitCalculatorsFactory extends AbstractKitCalculatorsFactory<Date> {

    private static final DateKitCalculatorsFactory DEFAULT = new DateKitCalculatorsFactory();

    private static final PeriodCountCalculator<Date> PCC = new DatePeriodCountCalculator();

    private static final DateIMMDateCalculator IMMDC = new DateIMMDateCalculator();

    public static DateKitCalculatorsFactory getDefaultInstance() {
        return DEFAULT;
    }

    public static DateDateCalculator currencyCalculator(final String ccy1, final String ccy2) {
        return DEFAULT.getCurrencyDateCalculator(ccy1, ccy2);
    }

    public static DateDateCalculator forwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD);
    }

    public static DateDateCalculator backwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.BACKWARD);
    }

    public static DateDateCalculator forwardUnlessMovingBackCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK);
    }

    public static DateDateCalculator modifiedFollowingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_FOLLOWING);
    }

    public static DateDateCalculator modifiedPrecedingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_PRECEDING);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public DateDateCalculator getCurrencyDateCalculator(String ccy1, String ccy2) {
        final CurrencyDateDateCalculator cal = new CurrencyDateDateCalculator(ccy1, ccy2);
        cal.setHolidayHandler(new DateForwardHandler());
        cal.setHolidayCalendars(getHolidayCalendar(ccy1), getHolidayCalendar(ccy2), getHolidayCalendar("USD"));
        return cal;
    }

    /*
     * (non-Javadoc)
     * 
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getDateCalculator(java.lang.String,
     *      java.lang.String)
     */
    public DateDateCalculator getDateCalculator(final String name, final String holidayHandlerType) {
        final DateDateCalculator cal = new DateDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        if (holidayHandlerType == null) {
            return cal;
        } else if (FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateForwardHandler());
        } else if (BACKWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateBackwardHandler());
        } else if (MODIFIED_FOLLOWING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateModifiedFollowingHandler());
        } else if (MODIFIED_PRECEDING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new DateModifiedPrecedingHandler());
        } else if (FORWARD_UNLESS_MOVING_BACK.equals(holidayHandlerType)) {
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
