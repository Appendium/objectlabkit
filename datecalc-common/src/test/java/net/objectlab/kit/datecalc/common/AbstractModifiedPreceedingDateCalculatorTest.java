package net.objectlab.kit.datecalc.common;

import java.util.Calendar;
import java.util.Set;

import junit.framework.Assert;

public abstract class AbstractModifiedPreceedingDateCalculatorTest<E> extends AbstractDateTestCase<E> {

    public void testSimpleForwardWithWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final E startDate = newDate("2006-08-01");
        cal.setStartDate(startDate);
        checkDate("Move by 0 days", cal.moveByDays(0), "2006-08-01");
        checkDate("Move by 1 days", cal.moveByDays(1), "2006-08-02");
        checkDate("Move by 1 more days", cal.moveByDays(1), "2006-08-03");
        checkDate("Move by 1 more more days", cal.moveByDays(1), "2006-08-04");
        checkDate("Move by 1 more more more days (across weekend)", cal.moveByDays(1), "2006-08-04");
    }

    public void testSimpleForwardStartDateWithWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        
        WorkingWeek ww = new WorkingWeek()
                .withWorkingDayFromCalendar(true, Calendar.SATURDAY)
                .withWorkingDayFromCalendar(true, Calendar.SUNDAY);
        
        cal.setWorkingWeek(getWorkingWeek(ww));
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        WorkingWeek ww = new WorkingWeek()
                .withWorkingDayFromCalendar(false, Calendar.MONDAY)
                .withWorkingDayFromCalendar(true, Calendar.TUESDAY)
                .withWorkingDayFromCalendar(false, Calendar.WEDNESDAY)
                .withWorkingDayFromCalendar(true, Calendar.THURSDAY)
                .withWorkingDayFromCalendar(false, Calendar.FRIDAY)
                .withWorkingDayFromCalendar(true, Calendar.SATURDAY)
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        WorkingWeek ww = new WorkingWeek()
                .withWorkingDayFromCalendar(false, Calendar.MONDAY)
                .withWorkingDayFromCalendar(true, Calendar.TUESDAY)
                .withWorkingDayFromCalendar(true, Calendar.WEDNESDAY)
                .withWorkingDayFromCalendar(true, Calendar.THURSDAY)
                .withWorkingDayFromCalendar(true, Calendar.FRIDAY)
                .withWorkingDayFromCalendar(false, Calendar.SATURDAY)
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

    public void testSimpleForwardWithHolidays() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        final Set<E> holidays = newHolidaysSet();
        Assert.assertEquals("Name", "bla", cal.getName());
        cal.setNonWorkingDays(holidays);
        Assert.assertEquals("Holidays", holidays, cal.getNonWorkingDays());
        Assert.assertEquals("Holidays size", 3, cal.getNonWorkingDays().size());

        Assert.assertTrue("contains", holidays.contains(newDate("2006-08-28")));
        Assert.assertTrue("contains", cal.getNonWorkingDays().contains(newDate("2006-08-28")));

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
     * @todo How to handle moveByBusDays if it moves backwards...
     * 
     */
    public void testMoveByBusinessDays() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        final Set<E> holidays = newHolidaysSet();
        Assert.assertEquals("Name", "bla", cal.getName());
        cal.setNonWorkingDays(holidays);
        Assert.assertEquals("Holidays", holidays, cal.getNonWorkingDays());
        Assert.assertEquals("Holidays size", 3, cal.getNonWorkingDays().size());

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

    public void testMoveByTenorDays() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 1D", cal.moveByTenor(StandardTenor.T_1D), "2006-08-09");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 2D", cal.moveByTenor(new Tenor(2, TenorCode.DAY)), "2006-08-10");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 10D", cal.moveByTenor(new Tenor(10, TenorCode.DAY)), "2006-08-18");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 11D", cal.moveByTenor(new Tenor(11, TenorCode.DAY)), "2006-08-18");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 12D", cal.moveByTenor(new Tenor(12, TenorCode.DAY)), "2006-08-18");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 13D", cal.moveByTenor(new Tenor(13, TenorCode.DAY)), "2006-08-21");

    }

    public void testMoveByTenorWeek() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 1W", cal.moveByTenor(StandardTenor.T_1W), "2006-08-15");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 2W", cal.moveByTenor(new Tenor(2, TenorCode.WEEK)), "2006-08-22");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 4W", cal.moveByTenor(new Tenor(4, TenorCode.WEEK)), "2006-09-05");
    }

    public void testAddAcrossMonth() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);

        cal.setStartDate(newDate("2006-08-02"));
        cal.moveByDays(-1);
        checkDate("1/8", cal, "2006-08-01");

        cal.moveByDays(-1);
        checkDate("do move to next month", cal, "2006-07-31");

        // now if it due to roll over:
        cal.setStartDate(newDate("2006-08-02"));
        final Set<E> holidays = newHolidaysSet();
        holidays.clear();
        holidays.add(newDate("2006-08-01"));
        cal.setNonWorkingDays(holidays);
        cal.moveByDays(-1);

        checkDate("do NOT move to next month", cal, "2006-08-02");
    }
    
}
