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
package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.YearMonthDay;

/**
 * A modified following handler will move the date forward if it falls on a non
 * working day BUT, if the new date falls into another month, it will revert to
 * moving backward until it finds a working day.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class YearMonthDayModifiedFollowingHandler implements HolidayHandler<YearMonthDay> {

    /**
     * Give the type name for this algorithm.
     * 
     * @return algorithm name.
     */
    public String getType() {
        return HolidayHandlerType.MODIFIED_FOLLOWING;
    }

    /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     * 
     * @param calculator
     *            the calculator
     * @return the date which may have moved.
     */
    public YearMonthDay moveCurrentDate(final DateCalculator<YearMonthDay> calculator) {
        return move(calculator, 1);
    }

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    protected YearMonthDay move(final DateCalculator<YearMonthDay> calculator, final int step) {
        YearMonthDay date = calculator.getCurrentBusinessDate();
        final int month = date.getMonthOfYear();
        int stepToUse = step;
        while (calculator.isNonWorkingDay(date)) {
            date = date.plusDays(stepToUse);
            if (date.getMonthOfYear() != month) {
                // flick to backward
                stepToUse *= -1;
                date = date.plusDays(stepToUse);
            }
        }
        return date;
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
