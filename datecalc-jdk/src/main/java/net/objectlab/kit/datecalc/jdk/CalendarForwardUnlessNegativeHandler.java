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
 * $Id: CalendarForwardHandler.java 203 2006-10-11 12:53:07Z benoitx $
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

import net.objectlab.kit.datecalc.common.BaseCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.NonWorkingDayChecker;

/**
 * A Jdk <code>Calendar</code> implementation of the
 * {@link net.objectlab.kit.datecalc.common.HolidayHandler}, for the
 * <strong>Forward</strong> algorithm.
 *
 * @author Marcin Jekot
 * @author Benoit Xhenseval
 */
public class CalendarForwardUnlessNegativeHandler implements HolidayHandler<Calendar> {

    /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     *
     * @param calculator
     *            the calculator
     * @return the date which may have moved.
     */
    public Calendar moveCurrentDate(final BaseCalculator<Calendar> calculator) {
        return adjustDate(calculator.getCurrentBusinessDate(), calculator.getCurrentIncrement(), calculator);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public Calendar adjustDate(final Calendar startDate, final int increment, final NonWorkingDayChecker<Calendar> checker) {
        final Calendar cal = (Calendar) startDate.clone();

        while (checker.isNonWorkingDay(cal)) {
            if (increment < 0) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            } else {
                cal.add(Calendar.DAY_OF_MONTH, 1);
            }
        }

        return cal;
    }

    /**
     * Give the type name for this algorithm.
     *
     * @return algorithm name.
     */
    public String getType() {
        return HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK;
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
