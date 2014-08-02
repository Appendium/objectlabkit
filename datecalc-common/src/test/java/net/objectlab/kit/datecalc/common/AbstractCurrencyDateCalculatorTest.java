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
package net.objectlab.kit.datecalc.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

public abstract class AbstractCurrencyDateCalculatorTest<E> extends AbstractDateTestCase<E> {

    protected HolidayCalendar<E> createRUHolidayCalendar() {
        final Set<E> us = new HashSet<E>();
        us.add(newDate("2006-01-06"));

        return new DefaultHolidayCalendar<E>(us, newDate("2005-01-01"), newDate("2021-12-31"));
    }

    protected HolidayCalendar<E> createEUHolidayCalendar() {
        final Set<E> us = new HashSet<E>();
        us.add(newDate("2006-01-02"));
        us.add(newDate("2006-12-26"));

        return new DefaultHolidayCalendar<E>(us, newDate("2005-01-01"), newDate("2021-12-31"));
    }

    protected HolidayCalendar<E> createMXHolidayCalendar() {
        final Set<E> us = new HashSet<E>();
        us.add(newDate("2006-07-06"));

        return new DefaultHolidayCalendar<E>(us, newDate("2005-01-01"), newDate("2021-12-31"));
    }

    protected DateCalculator<E> newCurrencyCalculator(final String ccy1, final String ccy2) {
        getDateCalculatorFactory().registerHolidays("GBP", createUKHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("USD", createUSHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("RUB", createRUHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("EUR", createEUHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("MXN", createMXHolidayCalendar());
        return getDateCalculatorFactory().getCurrencyDateCalculator(ccy1, ccy2);
    }

    public void testSimpleSpotWithWeekend() {
        final DateCalculator<E> cal = newCurrencyCalculator("USD", "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());

        final E startDate = newDate("2006-08-01");
        cal.setStartDate(startDate);
        checkDate("Move by 0 days", cal.moveByDays(0), "2006-08-01");
        checkDate("Move by 1 days", cal.moveByDays(1), "2006-08-02");
        checkDate("Move by 1 more days", cal.moveByDays(1), "2006-08-03");
        checkDate("Move by 1 more more days", cal.moveByDays(1), "2006-08-04");
        checkDate("Move by 1 more more more days (across weekend)", cal.moveByDays(1), "2006-08-07");
    }

    public void testSimpleForwardStartDateWithWeekend() {
        final DateCalculator<E> cal = newCurrencyCalculator("USD", "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());

        cal.setStartDate(newDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-07-31");

        cal.setStartDate(newDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-02");

        cal.setStartDate(newDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-04");

        cal.setStartDate(newDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-07");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-07");
    }

    public void testSimpleForwardStartDateNoWeekend() {
        final DateCalculator<E> cal = newCurrencyCalculator("USD", "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());
        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(true, Calendar.SATURDAY)
                .withWorkingDayFromCalendar(true, Calendar.SUNDAY);
        cal.setWorkingWeek(getWorkingWeek(ww));
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());

        cal.setStartDate(newDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-07-31");

        cal.setStartDate(newDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-02");

        cal.setStartDate(newDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-04");

        cal.setStartDate(newDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-05");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-06");
    }

    public void testSimpleForwardStartDateWhackyWeek() {
        final DateCalculator<E> cal = newCurrencyCalculator("USD", "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());

        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(false, Calendar.MONDAY)
                .withWorkingDayFromCalendar(true, Calendar.TUESDAY).withWorkingDayFromCalendar(false, Calendar.WEDNESDAY)
                .withWorkingDayFromCalendar(true, Calendar.THURSDAY).withWorkingDayFromCalendar(false, Calendar.FRIDAY)
                .withWorkingDayFromCalendar(true, Calendar.SATURDAY).withWorkingDayFromCalendar(false, Calendar.SUNDAY);
        cal.setWorkingWeek(getWorkingWeek(ww));

        cal.setStartDate(newDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-05");

        cal.setStartDate(newDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-05");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-08");
    }

    public void testSimpleForwardStartDateIdealWeekend() {
        final DateCalculator<E> cal = newCurrencyCalculator("USD", "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());

        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(false, Calendar.MONDAY)
                .withWorkingDayFromCalendar(true, Calendar.TUESDAY).withWorkingDayFromCalendar(true, Calendar.WEDNESDAY)
                .withWorkingDayFromCalendar(true, Calendar.THURSDAY).withWorkingDayFromCalendar(true, Calendar.FRIDAY)
                .withWorkingDayFromCalendar(false, Calendar.SATURDAY).withWorkingDayFromCalendar(false, Calendar.SUNDAY);
        cal.setWorkingWeek(getWorkingWeek(ww));

        cal.setStartDate(newDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-02");

        cal.setStartDate(newDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-04");

        cal.setStartDate(newDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-08");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-08");
    }

    public void testSimpleForwardWithHolidays() {
        final DateCalculator<E> cal = newCurrencyCalculator("USD", "GBP");
        Assert.assertEquals("Name", "USD.GBP", cal.getName());

        cal.setStartDate(newDate("2006-08-28"));
        checkDate("Move given Bank Holiday", cal, "2006-08-29");

        cal.setStartDate(newDate("2006-12-24"));
        checkDate("Xmas Eve", cal, "2006-12-27");

        cal.setStartDate(newDate("2006-12-21"));
        checkDate("21/12 + 1", cal.moveByDays(1), "2006-12-22");

        cal.setStartDate(newDate("2006-12-21"));
        checkDate("21/12 + 1", cal.moveByDays(2), "2006-12-27");

        cal.setStartDate(newDate("2006-12-22"));
        checkDate("22/12 + 1", cal.moveByDays(1), "2006-12-27");

        cal.setStartDate(newDate("2006-12-23"));
        checkDate("23/12 + 1", cal.moveByDays(1), "2006-12-28");
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testMoveByBusinessDays() {
        final DateCalculator<E> cal = newCurrencyCalculator("USD", "GBP");
        Assert.assertEquals("Name", "USD.GBP", cal.getName());

        cal.setStartDate(newDate("2006-08-24"));
        checkDate("Move 1 BD", cal.moveByBusinessDays(1), "2006-08-25");

        cal.setStartDate(newDate("2006-08-24"));
        checkDate("Add 1 week", cal.moveByDays(7), "2006-08-31");
        cal.setStartDate(newDate("2006-08-24"));
        checkDate("Move by 1W with 1 bank holiday", cal.moveByBusinessDays(7), "2006-09-05");

    }

    protected void checkMoveByTenor(final String ccy1, final String ccy2, final String startDate, final Tenor tenor, final int spotLag,
            final String expectedDate) {
        final DateCalculator<E> cal = newCurrencyCalculator(ccy1, ccy2);
        cal.setStartDate(newDate(startDate));
        checkDate("Move start:" + startDate + " tenor:" + tenor + " daysToSpot:" + spotLag, cal.moveByTenor(tenor, spotLag), expectedDate);
    }

    protected void checkMoveByTenor(final String ccy1, final String ccy2, final String startDate, final Tenor tenor, final String expectedDate,
            final String holidayHandlerType) {
        final DateCalculator<E> cal = newCurrencyCalculator(ccy1, ccy2);
        cal.setStartDate(newDate(startDate));
        checkDate("Move start:" + startDate + " tenor:" + tenor, cal.moveByTenor(tenor), expectedDate);
    }

    public void testMoveByTenorDaysZeroDayToSpot() {
        checkMoveByTenor("USD", "RUB", "2006-08-08", StandardTenor.SPOT, 0, "2006-08-08");
        checkMoveByTenor("USD", "RUB", "2006-01-06", StandardTenor.SPOT, 0, "2006-01-09"); // moved to Monday

        checkMoveByTenor("USD", "RUB", "2006-08-08", new Tenor(2, TenorCode.DAY), 0, "2006-08-10");
        checkMoveByTenor("USD", "RUB", "2006-01-06", new Tenor(2, TenorCode.DAY), 0, "2006-01-11");
    }

    /*
         July 2006
    Su Mo Tu We Th Fr Sa
    ....               1
    .2  3  4  5  6  7  8
    .9 10 11 12 13 14 15
    16 17 18 19 20 21 22
    23 24 25 26 27 28 29
    30 31

    August 2006
    Su Mo Tu We Th Fr Sa
    ..  .  1  2  3  4  5
    .6  7  8  9 10 11 12
    13 14 15 16 17 18 19
    20 21 22 23 24 25 26
    27 28 29 30 31
    */
    public void testMoveByTenorDaysOneDayToSpot() {
        checkMoveByTenor("USD", "CAD", "2006-07-05", StandardTenor.SPOT, 1, "2006-07-06");
        // US holiday on T+1
        checkMoveByTenor("USD", "CAD", "2006-07-03", StandardTenor.SPOT, 1, "2006-07-05");
        // TD on weekend, should be moved first
        checkMoveByTenor("USD", "CAD", "2006-07-01", StandardTenor.SPOT, 1, "2006-07-05");
        checkMoveByTenor("USD", "CAD", "2006-07-01", new Tenor(2, TenorCode.DAY), 1, "2006-07-07");
    }

    public void testMoveByTenorDaysTwoDaysToSpot() {
        // US holiday on 4 July!
        checkMoveByTenor("USD", "EUR", "2006-06-30", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor("EUR", "USD", "2006-06-30", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor("USD", "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-06");
        checkMoveByTenor("EUR", "USD", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-06");
        checkMoveByTenor("USD", "EUR", "2006-07-04", StandardTenor.SPOT, 2, "2006-07-07");
        checkMoveByTenor("EUR", "USD", "2006-07-04", StandardTenor.SPOT, 2, "2006-07-07");

        checkMoveByTenor("EUR", "USD", "2005-12-30", StandardTenor.SPOT, 2, "2006-01-04");
        checkMoveByTenor("USD", "EUR", "2005-12-30", StandardTenor.SPOT, 2, "2006-01-04");

        // cross Ccy with US Bank holiday on T+1 but it should not impact it
        checkMoveByTenor("GBP", "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor("EUR", "GBP", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-05");

        // cross Ccy with US Bank holiday on T+1 BUT ARS!!!
        checkMoveByTenor("ARS", "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-06");
        checkMoveByTenor("EUR", "ARS", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-06");

        // cross Ccy with US Bank holiday on T+1 BUT MXN!!! And MXN is on Holiday on 6 July
        checkMoveByTenor("MXN", "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-07");
        checkMoveByTenor("EUR", "MXN", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-07");

        // checkMoveByTenor("USD", "EUR", "2006-08-08", new Tenor(2, TenorCode.DAY), 2, "2006-08-14");
        // checkMoveByTenor("USD", "EUR", "2006-08-07", new Tenor(10, TenorCode.DAY), 2, "2006-08-21");
        // checkMoveByTenor("USD", "EUR", "2006-08-07", new Tenor(11, TenorCode.DAY), 2, "2006-08-21");
        // checkMoveByTenor("USD", "EUR", "2006-08-07", new Tenor(12, TenorCode.DAY), 2, "2006-08-21");
        // checkMoveByTenor("USD", "EUR", "2006-08-07", new Tenor(13, TenorCode.DAY), 2, "2006-08-22");
    }

    public void testCalculateTenorsZeroDaysToSpot() {
        List<Tenor> list = new ArrayList<Tenor>();
        list.add(StandardTenor.OVERNIGHT);
        list.add(StandardTenor.SPOT);
        list.add(StandardTenor.T_1D);
        list.add(StandardTenor.T_2D);
        list.add(StandardTenor.T_1W);
        list.add(StandardTenor.T_1M);
        list.add(StandardTenor.T_2M);
        list.add(StandardTenor.T_3M);
        list.add(StandardTenor.T_6M);
        list.add(StandardTenor.T_9M);
        list.add(StandardTenor.T_1Y);

        final DateCalculator<E> cal = newCurrencyCalculator("USD", "GBP");
        String startDate = "2006-08-24";
        cal.setStartDate(newDate(startDate));
        List<E> expectedResults = new ArrayList<E>();
        expectedResults.add(newDate("2006-08-25")); // ON
        expectedResults.add(newDate("2006-08-24")); // SPOT
        expectedResults.add(newDate("2006-08-25")); // 1D
        expectedResults.add(newDate("2006-08-29")); // 2D
        expectedResults.add(newDate("2006-08-31")); // 1W
        expectedResults.add(newDate("2006-09-25")); // 1M
        expectedResults.add(newDate("2006-10-24")); // 2M
        expectedResults.add(newDate("2006-11-24")); // 3M
        expectedResults.add(newDate("2007-02-26")); // 6M
        expectedResults.add(newDate("2007-05-24")); // 9M
        expectedResults.add(newDate("2007-08-24")); // 1Y

        List<E> results = cal.calculateTenorDates(list);
        assertEquals("Same size as tenor", list.size(), results.size());
        Iterator<E> it = results.iterator();
        Iterator<E> expected = expectedResults.iterator();
        for (Tenor tenor : list) {
            assertEquals("Move start:" + startDate + " tenor:" + tenor, expected.next(), it.next());
        }
    }

    public void testCalculateTenorsTwoDaysToSpot() {
        List<Tenor> list = new ArrayList<Tenor>();
        list.add(StandardTenor.OVERNIGHT);
        list.add(StandardTenor.SPOT);
        list.add(StandardTenor.T_1D);
        list.add(StandardTenor.T_2D);
        list.add(StandardTenor.T_1W);
        list.add(StandardTenor.T_1M);
        list.add(StandardTenor.T_2M);
        list.add(StandardTenor.T_3M);
        list.add(StandardTenor.T_6M);
        list.add(StandardTenor.T_9M);
        list.add(StandardTenor.T_1Y);

        final DateCalculator<E> cal = newCurrencyCalculator("USD", "GBP");
        String startDate = "2006-08-24";
        cal.setStartDate(newDate(startDate));
        List<E> expectedResults = new ArrayList<E>();
        expectedResults.add(newDate("2006-08-25")); // ON
        expectedResults.add(newDate("2006-08-29")); // SPOT
        expectedResults.add(newDate("2006-08-30")); // 1D
        expectedResults.add(newDate("2006-08-31")); // 2D
        expectedResults.add(newDate("2006-09-05")); // 1W
        expectedResults.add(newDate("2006-09-29")); // 1M
        expectedResults.add(newDate("2006-10-30")); // 2M
        expectedResults.add(newDate("2006-11-29")); // 3M
        expectedResults.add(newDate("2007-02-28")); // 6M - is this correct?
        expectedResults.add(newDate("2007-05-29")); // 9M
        expectedResults.add(newDate("2007-08-29")); // 1Y

        List<E> results = cal.calculateTenorDates(list, 2);
        assertEquals("Same size as tenor", list.size(), results.size());
        Iterator<E> it = results.iterator();
        Iterator<E> expected = expectedResults.iterator();
        for (Tenor tenor : list) {
            assertEquals("Move start:" + startDate + " tenor:" + tenor, expected.next(), it.next());
        }
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
