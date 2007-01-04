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
 * $Id: LocalDateForwardHandler.java 203 2006-10-11 12:53:07Z benoitx $
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

import org.joda.time.LocalDate;

/**
 * A Forward handler will move the date forward if it falls on a non working
 * day.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy: benoitx $
 * @version $Revision: 203 $ $Date: 2006-10-11 13:53:07 +0100 (Wed, 11 Oct 2006) $
 * 
 */
public class LocalDateForwardUnlessNegativeHandler implements HolidayHandler<LocalDate> {

    /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     * 
     * @param calculator
     *            the calculator
     * @return the date which may have moved.
     */
    public LocalDate moveCurrentDate(final DateCalculator<LocalDate> calculator) {
        return move(calculator, 1);
    }

    protected LocalDate move(final DateCalculator<LocalDate> calculator, final int step) {
        LocalDate date = calculator.getCurrentBusinessDate();
        while (calculator.isNonWorkingDay(date)) {
            if (calculator.getCurrentIncrement() < 0) {
                // act as a Backward calendar
                date = date.minusDays(step);
            } else {
                // move forward by a day!
                date = date.plusDays(step);
            }
        }
        return date;
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
