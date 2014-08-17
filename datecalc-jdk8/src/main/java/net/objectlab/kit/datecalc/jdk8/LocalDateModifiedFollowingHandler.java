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
package net.objectlab.kit.datecalc.jdk8;

import java.time.LocalDate;

import net.objectlab.kit.datecalc.common.BaseCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.NonWorkingDayChecker;

/**
 * A modified following handler will move the date forward if it falls on a non
 * working day BUT, if the new date falls into another month, it will revert to
 * moving backward until it finds a working day.
 *
 * @author Benoit Xhenseval
 *
 */
public class LocalDateModifiedFollowingHandler implements HolidayHandler<LocalDate> {

    /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     *
     * @param calculator
     *            the calculator
     * @return the date which may have moved.
     */
    @Override
    public LocalDate moveCurrentDate(final BaseCalculator<LocalDate> calculator) {
        return adjustDate(calculator.getCurrentBusinessDate(), 1, calculator);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------
    @Override
    public LocalDate adjustDate(LocalDate startDate, int increment, NonWorkingDayChecker<LocalDate> checker) {
        LocalDate date = startDate;
        final int month = date.getMonthValue();
        int stepToUse = increment;
        while (checker.isNonWorkingDay(date)) {
            date = date.plusDays(stepToUse);
            if (date.getMonthValue() != month) {
                // flick to backward
                stepToUse *= -1;
                date = date.plusDays(stepToUse);
            }
        }
        return date;
    }

    /**
     * Give the type name for this algorithm.
     *
     * @return algorithm name.
     */
    @Override
    public String getType() {
        return HolidayHandlerType.MODIFIED_FOLLOWING;
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
