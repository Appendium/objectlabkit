package net.objectlab.kit.datecalc.common;

import java.util.Calendar;
import java.util.Set;

import junit.framework.Assert;

public abstract class AbstractForwardDateCalculatorTest<E> extends AbstractDateTestCase<E> {

    public void testSimpleForwardWithWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final E startDate = newDate("2006-08-01");
        cal.setStartDate(startDate);
        checkDate("Move by 0 days", cal.moveByDays(0), "2006-08-01");
        checkDate("Move by 1 days", cal.moveByDays(1), "2006-08-02");
        checkDate("Move by 1 more days", cal.moveByDays(1), "2006-08-03");
        checkDate("Move by 1 more more days", cal.moveByDays(1), "2006-08-04");
        checkDate("Move by 1 more more more days (across weekend)", cal.moveByDays(1), "2006-08-07");
    }

    public void testSimpleForwardStartDateWithWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.FORWARD);
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
        checkDate("start date Saturday", cal, "2006-08-07");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-07");
    }

    public void testSimpleForwardStartDateNoWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.FORWARD);
        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(true, Calendar.SATURDAY).withWorkingDayFromCalendar(
                true, Calendar.SUNDAY);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(false, Calendar.MONDAY).withWorkingDayFromCalendar(
                true, Calendar.TUESDAY).withWorkingDayFromCalendar(false, Calendar.WEDNESDAY).withWorkingDayFromCalendar(true,
                Calendar.THURSDAY).withWorkingDayFromCalendar(false, Calendar.FRIDAY).withWorkingDayFromCalendar(true,
                Calendar.SATURDAY).withWorkingDayFromCalendar(false, Calendar.SUNDAY);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final WorkingWeek ww = new WorkingWeek().withWorkingDayFromCalendar(false, Calendar.MONDAY).withWorkingDayFromCalendar(
                true, Calendar.TUESDAY).withWorkingDayFromCalendar(true, Calendar.WEDNESDAY).withWorkingDayFromCalendar(true,
                Calendar.THURSDAY).withWorkingDayFromCalendar(true, Calendar.FRIDAY).withWorkingDayFromCalendar(false,
                Calendar.SATURDAY).withWorkingDayFromCalendar(false, Calendar.SUNDAY);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.FORWARD);
        final Set<E> holidays = newHolidaysSet();
        Assert.assertEquals("Name", "bla", cal.getName());
        cal.setNonWorkingDays(holidays);
        Assert.assertEquals("Holidays", holidays, cal.getNonWorkingDays());
        Assert.assertEquals("Holidays size", 3, cal.getNonWorkingDays().size());

        Assert.assertTrue("contains", holidays.contains(newDate("2006-08-28")));
        Assert.assertTrue("contains", cal.getNonWorkingDays().contains(newDate("2006-08-28")));

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

    public void testMoveByBusinessDays() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.FORWARD);
        final Set<E> holidays = newHolidaysSet();
        Assert.assertEquals("Name", "bla", cal.getName());
        cal.setNonWorkingDays(holidays);
        Assert.assertEquals("Holidays", holidays, cal.getNonWorkingDays());
        Assert.assertEquals("Holidays size", 3, cal.getNonWorkingDays().size());

        cal.setStartDate(newDate("2006-08-24"));
        checkDate("Move 1 BD", cal.moveByBusinessDays(1), "2006-08-25");

        cal.setStartDate(newDate("2006-08-24"));
        checkDate("Add 1 week", cal.moveByDays(7), "2006-08-31");
        cal.setStartDate(newDate("2006-08-24"));
        checkDate("Move by 1W with 1 bank holiday", cal.moveByBusinessDays(7), "2006-09-05");

    }

    public void testMoveByTenorDays() {
        checkMoveByTenor("2006-08-08", StandardTenor.T_1D, 0, "2006-08-09", HolidayHandlerType.FORWARD);
        checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.DAY), 0, "2006-08-10", HolidayHandlerType.FORWARD);
        checkMoveByTenor("2006-08-08", new Tenor(10, TenorCode.DAY), 0, "2006-08-18", HolidayHandlerType.FORWARD);
        checkMoveByTenor("2006-08-08", new Tenor(11, TenorCode.DAY), 0, "2006-08-21", HolidayHandlerType.FORWARD);
        checkMoveByTenor("2006-08-08", new Tenor(12, TenorCode.DAY), 0, "2006-08-21", HolidayHandlerType.FORWARD);
        checkMoveByTenor("2006-08-08", new Tenor(13, TenorCode.DAY), 0, "2006-08-21", HolidayHandlerType.FORWARD);
    }

    public void testMoveByTenorWeek() {
        checkMoveByTenor("2006-08-08", StandardTenor.T_1W, 0, "2006-08-15", HolidayHandlerType.FORWARD);
        checkMoveByTenor("2006-08-08", new Tenor(2, TenorCode.WEEK), 0, "2006-08-22", HolidayHandlerType.FORWARD);
        checkMoveByTenor("2006-08-08", new Tenor(4, TenorCode.WEEK), 0, "2006-09-05", HolidayHandlerType.FORWARD);
    }
}
