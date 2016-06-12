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
 * $Id: AbstractForwardDateCalculatorTest.java 238 2007-01-05 20:45:33Z benoitx $
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
package net.objectlab.kit.datecalc.common;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractPerformanceDateCalculatorTest<E extends Serializable> extends AbstractDateTestCase<E> {
    private static final int REPEAT = 100000;
    private static final Map<String, String> RESULTS = new TreeMap<String, String>();

    public void testMoveByBusDate1Cal() {
        registerHolidays("UK", createUKHolidayCalendar());
        final DateCalculator<E> cal = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final E startDate = newDate("2006-08-01");
        cal.setStartDate(startDate);
        final long start = System.currentTimeMillis();
        for (int i = 0; i < REPEAT; i++) {
            cal.moveByBusinessDays(1);
            cal.getCurrentBusinessDate();
        }
        final long stop = System.currentTimeMillis();
        keepResults("testMoveByBusDate", start, stop);
    }

    private void keepResults(final String string, final long start, final long stop) {
        RESULTS.put(string + " " + getClass().getSimpleName(), REPEAT + " = " + (stop - start) + " ms");
        System.out.println("\nPERFORMANCE\n");
        for (final String str : RESULTS.keySet()) {
            System.out.println("+++++++ " + str + " " + RESULTS.get(str));
        }
    }

    public void testSetDateThenMoveByBusDate1Cal() {
        registerHolidays("UK", createUKHolidayCalendar());
        final DateCalculator<E> cal = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final E startDate = newDate("2006-08-01");
        final long start = System.currentTimeMillis();
        for (int i = 0; i < REPEAT; i++) {
            cal.setStartDate(startDate);
            cal.moveByBusinessDays(10);
            cal.getCurrentBusinessDate();
        }
        final long stop = System.currentTimeMillis();
        keepResults("testSetDateThenMoveByBusDate", start, stop);
    }

    public void testMoveByBusDate2Calendars() {
        registerHolidays("UK", createUKHolidayCalendar());
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final E startDate = newDate("2006-08-01");
        final DateCalculator<E> cal = cal1.combine(cal2);
        cal.setStartDate(startDate);
        final long start = System.currentTimeMillis();
        for (int i = 0; i < REPEAT; i++) {
            cal.moveByBusinessDays(1);
            cal.getCurrentBusinessDate();
        }
        final long stop = System.currentTimeMillis();
        keepResults("testMoveByBusDate2Calendars", start, stop);
    }

    public void testCombineCalThenMoveByBusDate() {
        registerHolidays("UK", createUKHolidayCalendar());
        registerHolidays("US", createUSHolidayCalendar());
        final DateCalculator<E> cal1 = newDateCalculator("US", HolidayHandlerType.FORWARD);
        final E localDate = newDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<E> cal2 = newDateCalculator("UK", HolidayHandlerType.FORWARD);

        final E startDate = newDate("2006-08-01");
        final long start = System.currentTimeMillis();
        for (int i = 0; i < REPEAT; i++) {
            final DateCalculator<E> cal = cal1.combine(cal2);
            cal.setStartDate(startDate);
            cal.moveByBusinessDays(10);
            cal.getCurrentBusinessDate();
        }
        final long stop = System.currentTimeMillis();
        keepResults("testCombineCalThenMoveByBusDate", start, stop);
    }

    @Override
    protected HolidayCalendar<E> createUKHolidayCalendar() {
        final HolidayCalendar<E> cal = super.createUKHolidayCalendar();
        cal.setLateBoundary(newDate("9999-12-31"));
        return cal;
    }

    @Override
    protected HolidayCalendar<E> createUSHolidayCalendar() {
        final HolidayCalendar<E> cal = super.createUSHolidayCalendar();
        cal.setLateBoundary(newDate("9999-12-31"));
        return cal;
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
