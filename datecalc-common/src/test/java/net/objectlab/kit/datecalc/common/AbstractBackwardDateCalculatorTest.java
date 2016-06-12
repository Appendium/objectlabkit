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
import java.util.Calendar;

import junit.framework.Assert;

public abstract class AbstractBackwardDateCalculatorTest<E extends Serializable> extends AbstractDateTestCase<E> {

    public void testSimpleForwardWithWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());
        assertNull("Early boundary", cal.getHolidayCalendar().getEarlyBoundary());
        assertNull("Late boundary", cal.getHolidayCalendar().getLateBoundary());

        final E startDate = newDate("2006-08-01");
        cal.setStartDate(startDate);
        checkDate("Move by 0 days", cal.moveByDays(0), "2006-08-01");
        checkDate("Move by 1 days", cal.moveByDays(1), "2006-08-02");
        checkDate("Move by 1 more days", cal.moveByDays(1), "2006-08-03");
        checkDate("Move by 1 more more days", cal.moveByDays(1), "2006-08-04");
        checkDate("Move by 1 more more more days (across weekend)", cal.moveByDays(1), "2006-08-04");
    }

    public void testSimpleBackwardStartDateWithWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
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
        checkDate("start date Saturday", cal, "2006-08-04");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-04");
    }

    public void testSimpleForwardStartDateNoWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(true, Calendar.SATURDAY).withWorkingDayFromCalendar(true,
                Calendar.SUNDAY);
        cal.setWorkingWeek(getWorkingWeek(ww));
        Assert.assertEquals("Name", "bla", cal.getName());
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());

        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(false, Calendar.MONDAY).withWorkingDayFromCalendar(true, Calendar.TUESDAY)
                .withWorkingDayFromCalendar(false, Calendar.WEDNESDAY).withWorkingDayFromCalendar(true, Calendar.THURSDAY)
                .withWorkingDayFromCalendar(false, Calendar.FRIDAY).withWorkingDayFromCalendar(true, Calendar.SATURDAY)
                .withWorkingDayFromCalendar(false, Calendar.SUNDAY);
        cal.setWorkingWeek(getWorkingWeek(ww));

        cal.setStartDate(newDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-07-29");

        cal.setStartDate(newDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-05");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-05");
    }

    public void testSimpleForwardStartDateIdealWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getHolidayCalendar().getHolidays().size());

        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(false, Calendar.MONDAY).withWorkingDayFromCalendar(true, Calendar.TUESDAY)
                .withWorkingDayFromCalendar(true, Calendar.WEDNESDAY).withWorkingDayFromCalendar(true, Calendar.THURSDAY)
                .withWorkingDayFromCalendar(true, Calendar.FRIDAY).withWorkingDayFromCalendar(false, Calendar.SATURDAY)
                .withWorkingDayFromCalendar(false, Calendar.SUNDAY);
        cal.setWorkingWeek(getWorkingWeek(ww));

        cal.setStartDate(newDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-07-28");

        cal.setStartDate(newDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(newDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-02");

        cal.setStartDate(newDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(newDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-04");

        cal.setStartDate(newDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-04");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-04");
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testSimpleForwardWithHolidays() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        final HolidayCalendar<E> holidays = newHolidaysCalendar();
        Assert.assertEquals("Name", "bla", cal.getName());
        cal.setHolidayCalendar(holidays);
        Assert.assertEquals("Holidays", holidays.getHolidays(), cal.getHolidayCalendar().getHolidays());
        Assert.assertEquals("Holidays size", 3, cal.getHolidayCalendar().getHolidays().size());

        Assert.assertTrue("contains", holidays.isHoliday(newDate("2006-08-28")));
        Assert.assertTrue("contains", cal.getHolidayCalendar().isHoliday(newDate("2006-08-28")));

        cal.setStartDate(newDate("2006-08-28"));
        checkDate("Move given Bank Holiday", cal, "2006-08-25");

        cal.setStartDate(newDate("2006-12-24"));
        checkDate("Xmas Eve", cal, "2006-12-22");

        cal.setStartDate(newDate("2006-12-21"));
        checkDate("21/12 + 1", cal.moveByDays(1), "2006-12-22");

        cal.setStartDate(newDate("2006-12-21"));
        checkDate("21/12 + 1", cal.moveByDays(2), "2006-12-22");

        cal.setStartDate(newDate("2006-12-22"));
        checkDate("22/12 + 1", cal.moveByDays(1), "2006-12-22");

        cal.setStartDate(newDate("2006-12-23"));
        checkDate("23/12 + 1", cal.moveByDays(1), "2006-12-22");
    }

    /**
     * not sure what to expect from this... with backward mechanism...
     */
    public void testMoveByBusinessDays() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.BACKWARD);
        final HolidayCalendar<E> holidays = newHolidaysCalendar();
        Assert.assertEquals("Name", "bla", cal.getName());
        cal.setHolidayCalendar(holidays);
        Assert.assertEquals("Holidays", holidays.getHolidays(), cal.getHolidayCalendar().getHolidays());
        Assert.assertEquals("Holidays size", 3, cal.getHolidayCalendar().getHolidays().size());

        // cal.setStartDate(newDate("2006-08-24"));
        // checkDate("Move 1 BD", cal.moveByBusinessDays(1), "2006-08-25");

        cal.setStartDate(newDate("2006-08-24"));
        try {
            cal.moveByBusinessDays(7);
            fail("Should have thrown exception");
        } catch (final IllegalArgumentException e) {
            // ok
        }
        // checkDate("Add 1 week", cal.moveByDays(7), "2006-08-31");
        // cal.setStartDate(newDate("2006-08-24"));
        // checkDate("Move by 1W with 1 bank holiday",
        // cal.moveByBusinessDays(7), "2006-09-05");

    }

    /*    public void testMoveByTenorDays() {
            checkMoveByTenor("2006-08-08", StandardTenor.T_1D, 0, "2006-08-09", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.DAY), 0, "2006-08-10", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(10, TenorCode.DAY), 0, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(11, TenorCode.DAY), 0, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(12, TenorCode.DAY), 0, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(13, TenorCode.DAY), 0, "2006-08-21", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-09-26", new Tenor(4, TenorCode.DAY), 0, "2006-09-29", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorDaysOneDayToSpot() {
            checkMoveByTenor("2006-08-08", StandardTenor.T_1D, 1, "2006-08-10", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.DAY), 1, "2006-08-11", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(10, TenorCode.DAY), 1, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(11, TenorCode.DAY), 1, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(12, TenorCode.DAY), 1, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(13, TenorCode.DAY), 1, "2006-08-21", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorDaysTwoDaysToSpot() {
            checkMoveByTenor("2006-08-08", StandardTenor.T_1D, 2, "2006-08-11", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.DAY), 2, "2006-08-11", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(10, TenorCode.DAY), 2, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(11, TenorCode.DAY), 2, "2006-08-18", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(12, TenorCode.DAY), 2, "2006-08-21", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-07", new Tenor(13, TenorCode.DAY), 2, "2006-08-22", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorWeek() {
            checkMoveByTenor("2006-08-08", StandardTenor.T_1W, 0, "2006-08-15", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.WEEK), 0, "2006-08-22", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(4, TenorCode.WEEK), 0, "2006-09-05", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorWeekOneDayToSpot() {
            checkMoveByTenor("2006-08-08", StandardTenor.T_1W, 1, "2006-08-16", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.WEEK), 1, "2006-08-23", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(4, TenorCode.WEEK), 1, "2006-09-06", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorWeekTwoDaysToSpot() {
            checkMoveByTenor("2006-08-08", StandardTenor.T_1W, 2, "2006-08-17", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.WEEK), 2, "2006-08-24", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(4, TenorCode.WEEK), 2, "2006-09-07", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorMonth() {
            checkMoveByTenor("2006-08-31", StandardTenor.T_1M, 0, "2006-09-29", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.T_2M, 0, "2006-10-31", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-01-31", StandardTenor.T_1M, 0, "2006-02-28", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-01-31", StandardTenor.T_1M, 0, "2008-02-29", HolidayHandlerType.BACKWARD);
    
            checkMoveByTenor("2006-08-08", StandardTenor.T_1M, 0, "2006-09-08", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-09", StandardTenor.T_1M, 0, "2006-09-08", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.MONTH), 0, "2006-10-06", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(5, TenorCode.MONTH), 0, "2007-01-08", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorMonthOneDayToSpot() {
            checkMoveByTenor("2006-08-31", StandardTenor.T_1M, 1, "2006-09-29", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.T_2M, 1, "2006-11-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-01-31", StandardTenor.T_1M, 1, "2006-03-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-01-31", StandardTenor.T_1M, 1, "2008-02-29", HolidayHandlerType.BACKWARD);
    
            checkMoveByTenor("2006-08-08", StandardTenor.T_1M, 1, "2006-09-08", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-09", StandardTenor.T_1M, 1, "2006-09-08", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.MONTH), 1, "2006-10-09", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(5, TenorCode.MONTH), 1, "2007-01-09", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorMonthTwoDaysToSpot() {
            checkMoveByTenor("2006-08-31", StandardTenor.T_1M, 2, "2006-09-29", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.T_2M, 2, "2006-11-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-01-31", StandardTenor.T_1M, 2, "2006-03-02", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-01-31", StandardTenor.T_1M, 2, "2008-02-29", HolidayHandlerType.BACKWARD);
    
            checkMoveByTenor("2006-08-08", StandardTenor.T_1M, 2, "2006-09-08", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-09", StandardTenor.T_1M, 2, "2006-09-11", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.MONTH), 2, "2006-10-10", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-08", new Tenor(5, TenorCode.MONTH), 2, "2007-01-10", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorYear() {
            checkMoveByTenor("2006-08-31", StandardTenor.T_1Y, 0, "2007-08-31", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.T_2Y, 0, "2008-08-29", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-02-29", StandardTenor.T_1Y, 0, "2009-02-27", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-02-29", StandardTenor.T_4Y, 0, "2012-02-29", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorYearOneDayToSpot() {
            checkMoveByTenor("2006-08-31", StandardTenor.T_1Y, 1, "2007-08-31", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.T_2Y, 1, "2008-09-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-02-29", StandardTenor.T_1Y, 1, "2009-02-27", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-02-29", StandardTenor.T_4Y, 1, "2012-02-29", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorYearTwoDaysToSpot() {
            checkMoveByTenor("2006-08-31", StandardTenor.T_1Y, 2, "2007-08-31", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.T_2Y, 2, "2008-09-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-02-29", StandardTenor.T_1Y, 2, "2009-02-27", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2008-02-29", StandardTenor.T_4Y, 2, "2012-02-29", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorSpot() {
            checkMoveByTenor("2006-08-31", StandardTenor.SPOT, 0, "2006-08-31", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-28", StandardTenor.SPOT, 0, "2006-08-25", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorSpotOneDayToSpot() {
            checkMoveByTenor("2006-08-31", StandardTenor.SPOT, 1, "2006-09-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-28", StandardTenor.SPOT, 1, "2006-08-25", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorSpotTwoDaysToSpot() {
            checkMoveByTenor("2006-08-31", StandardTenor.SPOT, 2, "2006-09-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-28", StandardTenor.SPOT, 2, "2006-08-25", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorOvernight() {
            checkMoveByTenor("2006-08-24", StandardTenor.OVERNIGHT, 0, "2006-08-25", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-25", StandardTenor.OVERNIGHT, 0, "2006-08-25", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.OVERNIGHT, 0, "2006-09-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-28", StandardTenor.OVERNIGHT, 0, "2006-08-25", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorOvernightOneDayToSpot() {
            checkMoveByTenor("2006-08-24", StandardTenor.OVERNIGHT, 1, "2006-08-25", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-25", StandardTenor.OVERNIGHT, 1, "2006-08-25", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.OVERNIGHT, 1, "2006-09-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-28", StandardTenor.OVERNIGHT, 1, "2006-08-25", HolidayHandlerType.BACKWARD);
        }
    
        public void testMoveByTenorOvernightTwoDaysToSpot() {
            checkMoveByTenor("2006-08-24", StandardTenor.OVERNIGHT, 2, "2006-08-25", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-25", StandardTenor.OVERNIGHT, 2, "2006-08-25", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-31", StandardTenor.OVERNIGHT, 2, "2006-09-01", HolidayHandlerType.BACKWARD);
            checkMoveByTenor("2006-08-28", StandardTenor.OVERNIGHT, 2, "2006-08-25", HolidayHandlerType.BACKWARD);
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
