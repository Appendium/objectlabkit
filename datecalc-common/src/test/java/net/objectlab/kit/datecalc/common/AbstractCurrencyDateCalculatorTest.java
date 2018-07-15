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

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

public abstract class AbstractCurrencyDateCalculatorTest<E extends Serializable> extends AbstractDateTestCase<E> {

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

    @Override
    public void tearDown() {
        getDateCalculatorFactory().unregisterAllHolidayCalendars();
    }

    protected CurrencyDateCalculator<E> newCurrencyCalculator(final String ccy1, final String ccy2) {
        getDateCalculatorFactory().registerHolidays("GBP", createUKHolidayCalendar());
        getDateCalculatorFactory().registerHolidays(CalculatorConstants.USD_CODE, createUSHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("RUB", createRUHolidayCalendar());
        getDateCalculatorFactory().registerHolidays("EUR", createEUHolidayCalendar());
        return getDateCalculatorFactory().getDefaultCurrencyDateCalculator(ccy1, ccy2, SpotLag.T_2);
    }

    /*
     *     August 2006
     * Su Mo Tu We Th Fr Sa
     *        1  2  3  4  5
     *  6  7  8  9 10 11 12
     * 13 14 15 16 17 18 19
     * 20 21 22 23 24 25 26
     * 27 28 29 30 31
     */
    public void testSimpleSpotUsdEur() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CalculatorConstants.USD_CODE, "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());

        checkDate("Spot", cal.calculateSpotDate(newDate("2006-08-01")), "2006-08-03"); // Tues->Thur
        checkDate("Spot", cal.calculateSpotDate(newDate("2006-08-02")), "2006-08-04"); // Wed -> Fri
        checkDate("Spot", cal.calculateSpotDate(newDate("2006-08-03")), "2006-08-07"); // Thu -> Mon
        checkDate("Spot", cal.calculateSpotDate(newDate("2006-08-04")), "2006-08-08"); // Fri -> Tue
        checkDate("Spot", cal.calculateSpotDate(newDate("2006-08-05")), "2006-08-09"); // Sat (move to Mon) -> Wed
    }

    public void testSimpleTodayUsdEur() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CalculatorConstants.USD_CODE, "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());

        checkDate("Spot", cal.calculateTenorDate(newDate("2006-08-01"), StandardTenor.OVERNIGHT), "2006-08-01"); // Tues->Tues
    }

    public void testSimpleTomUsdEur() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CalculatorConstants.USD_CODE, "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());

        final Tenor tom = new Tenor(0, TenorCode.TOM_NEXT);

        checkDate("Spot", cal.calculateTenorDate(newDate("2006-08-01"), tom), "2006-08-02"); // Tues-> Wed
        checkDate("Spot", cal.calculateTenorDate(newDate("2006-08-04"), tom), "2006-08-07"); // Fri -> Mon
    }

    /*
     * July 2006
     * Su Mo Tu We Th Fr Sa
     * ....               1
     * .2  3  4  5  6  7  8
     * .9 10 11 12 13 14 15
     * 16 17 18 19 20 21 22
     * 23 24 25 26 27 28 29
     * 30 31
     */
    public void testSimpleSpotUsdEurWithUSDHoliday() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CalculatorConstants.USD_CODE, "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());

        checkDate("Spot from " + newDate("2006-06-30"), cal.calculateSpotDate(newDate("2006-06-30")), "2006-07-05"); // Fri -> Wed
        checkDate("Spot from " + newDate("2006-07-01"), cal.calculateSpotDate(newDate("2006-07-01")), "2006-07-05"); // Sat -> Wed
        checkDate("Spot from " + newDate("2006-07-03"), cal.calculateSpotDate(newDate("2006-07-03")), "2006-07-05"); // Mon -> Wed
        checkDate("Spot from " + newDate("2006-07-04"), cal.calculateSpotDate(newDate("2006-07-04")), "2006-07-07"); // Tue -> Fri
    }

    public void testCrossEurGbp() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator("EUR", "GBP");
        checkDate("Spot from " + newDate("2006-06-30"), cal.calculateSpotDate(newDate("2006-06-30")), "2006-07-05"); // Fri->Wed (use USD!)
        checkDate("Spot from " + newDate("2006-07-02"), cal.calculateSpotDate(newDate("2006-07-02")), "2006-07-05"); // Sun->Wed
        checkDate("Spot from " + newDate("2006-07-03"), cal.calculateSpotDate(newDate("2006-07-03")), "2006-07-05"); // Mon->Wed
    }

    public void testCrossEurGbpButDoNotUseUsd() {
        final CurrencyDateCalculator<E> cal = getDateCalculatorFactory().buildCurrencyDateCalculator(
                getDateCalculatorFactory().getDefaultCurrencyDateCalculatorBuilder("EUR", "GBP", SpotLag.T_2).brokenDateAllowed(true));
        checkDate("Spot from " + newDate("2006-06-30"), cal.calculateSpotDate(newDate("2006-06-30")), "2006-07-04"); // Fri->Tue (do NOT use USD!)
        checkDate("Spot from " + newDate("2006-07-02"), cal.calculateSpotDate(newDate("2006-07-02")), "2006-07-05"); // Sun->Wed
        checkDate("Spot from " + newDate("2006-07-03"), cal.calculateSpotDate(newDate("2006-07-03")), "2006-07-05"); // Mon->Wed
    }

    public void testCrossEurGbpButDoNotAdjustStartDate() {
        final CurrencyDateCalculator<E> cal = getDateCalculatorFactory().buildCurrencyDateCalculator(getDateCalculatorFactory()
                .getDefaultCurrencyDateCalculatorBuilder("EUR", "GBP", SpotLag.T_2).adjustStartDateWithCurrencyPair(false).brokenDateAllowed(true));
        checkDate("Spot from " + newDate("2006-07-09"), cal.calculateSpotDate(newDate("2006-07-09")), "2006-07-11"); // Sun->Tue
        checkDate("Spot from " + newDate("2006-07-03"), cal.calculateSpotDate(newDate("2006-07-03")), "2006-07-05"); // Mon->Wed
    }

    public void testCrossEurMxnUsingUsdT1() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator("EUR", "MXN");
        checkDate("Spot from " + cal.getName() + " " + newDate("2006-06-30"), cal.calculateSpotDate(newDate("2006-06-30")), "2006-07-05"); // Fri->Wed
        // (use
        // USD!)
        final E spot = cal.calculateSpotDate(newDate("2006-07-02"));
        checkDate("Spot from " + cal.getName() + " " + newDate("2006-07-02"), spot, "2006-07-06"); // Sun->Wed
        checkDate("Spot from " + cal.getName() + " " + newDate("2006-07-03"), cal.calculateSpotDate(newDate("2006-07-03")), "2006-07-06"); // Mon->Thu
    }

    public void testSimpleUsdJod() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CalculatorConstants.USD_CODE, "JOD"); // 3 day weekend!
        checkDate("Spot from " + cal.getName() + " " + newDate("2006-07-06"), cal.calculateSpotDate(newDate("2006-07-06")), "2006-07-11");
    }

    public void testSimpleUsdAed() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CalculatorConstants.USD_CODE, "AED"); // mix of weeks!
        checkDate("Spot from " + cal.getName() + " " + newDate("2006-07-06"), cal.calculateSpotDate(newDate("2006-07-06")), "2006-07-10");
    }

    public void testCrossGbpJpyWithHolidays() {
        final Set<E> xxxHholidays = new HashSet<E>();
        xxxHholidays.add(newDate("2014-08-04"));
        final HolidayCalendar<E> xxxCalendar = new DefaultHolidayCalendar<E>(xxxHholidays, newDate("2014-01-01"), newDate("2014-12-31"));
        getDateCalculatorFactory().registerHolidays("XXX", xxxCalendar);

        final Set<E> yyyHholidays = new HashSet<E>();
        yyyHholidays.add(newDate("2014-08-05"));
        final HolidayCalendar<E> yyyCalendar = new DefaultHolidayCalendar<E>(yyyHholidays, newDate("2014-01-01"), newDate("2014-12-31"));
        getDateCalculatorFactory().registerHolidays("YYY", yyyCalendar);

        final CurrencyDateCalculator<E> calc = newCurrencyCalculator("XXX", "YYY");

        // set startDate, this will also set the current business date.
        System.out.println(
                calc.getName() + " TD: " + newDate("2014-08-01") + " Spot " + calc.calculateSpotDate(newDate("2014-08-01")) + " expects 6 Aug");
    }

    public void testTenorNoHolidayModifiedFollowing() {
        final CurrencyDateCalculator<E> cal = getDateCalculatorFactory()
                .buildCurrencyDateCalculator(getDateCalculatorFactory().getDefaultCurrencyDateCalculatorBuilder("EUR", "USD", SpotLag.T_2)//
        );

        E startDate = newDate("2014-06-26");
        E spotDate = cal.calculateSpotDate(startDate);
        checkDate("Spot from 26-Jun-2014", spotDate, "2014-06-30");
        E t1mDate = cal.calculateTenorDate(startDate, StandardTenor.T_1M);
        checkDate("1M from 26-Jun-2014", t1mDate, "2014-07-30");

        startDate = newDate("2014-10-28");

        spotDate = cal.calculateSpotDate(startDate);
        checkDate("Spot from 28-Oct-2014", spotDate, "2014-10-30");
        t1mDate = cal.calculateTenorDate(startDate, StandardTenor.T_1M);
        checkDate("1M from 28-Oct-2014", t1mDate, "2014-11-28");
    }

    public void testTenorNoHolidayForward() {
        final CurrencyDateCalculator<E> cal = getDateCalculatorFactory()
                .buildCurrencyDateCalculator(getDateCalculatorFactory().getDefaultCurrencyDateCalculatorBuilder("EUR", "USD", SpotLag.T_2)//
                        .tenorHolidayHandler(getDateCalculatorFactory().getHolidayHandler(HolidayHandlerType.FORWARD)) //
        );

        E startDate = newDate("2014-10-28");

        E spotDate = cal.calculateSpotDate(startDate);
        checkDate("Spot from 28-Oct-2014", spotDate, "2014-10-30");
        E t1mDate = cal.calculateTenorDate(startDate, StandardTenor.T_1M);
        checkDate("1M from 28-Oct-2014", t1mDate, "2014-12-01"); // ALLOWED to Cross over to next month
    }
    /*
    
    public void testSimpleForwardStartDateWithWeekend() {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());
    
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
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "EUR");
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
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "EUR");
        Assert.assertEquals("Name", "USD.EUR", cal.getName());
    
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
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "EUR");
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
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "GBP");
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
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "GBP");
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
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(ccy1, ccy2);
        cal.setStartDate(newDate(startDate));
        checkDate("Move start:" + startDate + " tenor:" + tenor + " daysToSpot:" + spotLag, cal.moveByTenor(tenor, spotLag), expectedDate);
    }
    
    protected void checkMoveByTenor(final String ccy1, final String ccy2, final String startDate, final Tenor tenor, final String expectedDate,
            final String holidayHandlerType) {
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(ccy1, ccy2);
        cal.setStartDate(newDate(startDate));
        checkDate("Move start:" + startDate + " tenor:" + tenor, cal.moveByTenor(tenor), expectedDate);
    }
    
    public void testMoveByTenorDaysZeroDayToSpot() {
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "RUB", "2006-08-08", StandardTenor.SPOT, 0, "2006-08-08");
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "RUB", "2006-01-06", StandardTenor.SPOT, 0, "2006-01-09"); // moved to Monday
    
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "RUB", "2006-08-08", new Tenor(2, TenorCode.DAY), 0, "2006-08-10");
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "RUB", "2006-01-06", new Tenor(2, TenorCode.DAY), 0, "2006-01-11");
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
    /*
    public void testMoveByTenorDaysOneDayToSpot() {
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "CAD", "2006-07-05", StandardTenor.SPOT, 1, "2006-07-06");
        // US holiday on T+1
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "CAD", "2006-07-03", StandardTenor.SPOT, 1, "2006-07-05");
        // TD on weekend, should be moved first
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "CAD", "2006-07-01", StandardTenor.SPOT, 1, "2006-07-05");
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "CAD", "2006-07-01", new Tenor(2, TenorCode.DAY), 1, "2006-07-07");
    }
    
    public void testMoveByTenorDaysTwoDaysToSpot() {
        // US holiday on 4 July!
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-06-30", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor("EUR", CurrencyDateCalculator.USD_CODE, "2006-06-30", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor("EUR", CurrencyDateCalculator.USD_CODE, "2006-07-03", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-07-04", StandardTenor.SPOT, 2, "2006-07-07");
        checkMoveByTenor("EUR", CurrencyDateCalculator.USD_CODE, "2006-07-04", StandardTenor.SPOT, 2, "2006-07-07");
    
        checkMoveByTenor("EUR", CurrencyDateCalculator.USD_CODE, "2005-12-30", StandardTenor.SPOT, 2, "2006-01-04");
        checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2005-12-30", StandardTenor.SPOT, 2, "2006-01-04");
    
        // cross Ccy with US Bank holiday on T+1 but it should not impact it
        checkMoveByTenor("GBP", "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-05");
        checkMoveByTenor("EUR", "GBP", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-05");
    
        // cross Ccy with US Bank holiday on T+1 BUT ARS!!!
        checkMoveByTenor("ARS", "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-06");
        checkMoveByTenor("EUR", "ARS", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-06");
    
        // cross Ccy with US Bank holiday on T+1 BUT MXN!!! And MXN is on Holiday on 6 July
        checkMoveByTenor("MXN", "EUR", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-07");
        checkMoveByTenor("EUR", "MXN", "2006-07-03", StandardTenor.SPOT, 2, "2006-07-07");
    
        // checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-08-08", new Tenor(2, TenorCode.DAY), 2, "2006-08-14");
        // checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-08-07", new Tenor(10, TenorCode.DAY), 2, "2006-08-21");
        // checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-08-07", new Tenor(11, TenorCode.DAY), 2, "2006-08-21");
        // checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-08-07", new Tenor(12, TenorCode.DAY), 2, "2006-08-21");
        // checkMoveByTenor(CurrencyDateCalculator.USD_CODE, "EUR", "2006-08-07", new Tenor(13, TenorCode.DAY), 2, "2006-08-22");
    }
    
    public void testCalculateTenorsZeroDaysToSpot() {
        final List<Tenor> list = new ArrayList<Tenor>();
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
    
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "GBP");
        final String startDate = "2006-08-24";
        cal.setStartDate(newDate(startDate));
        final List<E> expectedResults = new ArrayList<E>();
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
    
        final List<E> results = cal.calculateTenorDates(list);
        assertEquals("Same size as tenor", list.size(), results.size());
        final Iterator<E> it = results.iterator();
        final Iterator<E> expected = expectedResults.iterator();
        for (final Tenor tenor : list) {
            assertEquals("Move start:" + startDate + " tenor:" + tenor, expected.next(), it.next());
        }
    }
    
    public void testCalculateTenorsTwoDaysToSpot() {
        final List<Tenor> list = new ArrayList<Tenor>();
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
    
        final CurrencyDateCalculator<E> cal = newCurrencyCalculator(CurrencyDateCalculator.USD_CODE, "GBP");
        final String startDate = "2006-08-24";
        cal.setStartDate(newDate(startDate));
        final List<E> expectedResults = new ArrayList<E>();
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
    
        final List<E> results = cal.calculateTenorDates(list, 2);
        assertEquals("Same size as tenor", list.size(), results.size());
        final Iterator<E> it = results.iterator();
        final Iterator<E> expected = expectedResults.iterator();
        for (final Tenor tenor : list) {
            assertEquals("Move start:" + startDate + " tenor:" + tenor, expected.next(), it.next());
        }
    }
     */
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
