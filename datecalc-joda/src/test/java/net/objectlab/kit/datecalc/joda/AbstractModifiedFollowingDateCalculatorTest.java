package net.objectlab.kit.datecalc.joda;

import java.util.Set;

import junit.framework.Assert;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.StandardTenor;
import net.objectlab.kit.datecalc.common.Tenor;
import net.objectlab.kit.datecalc.common.TenorCode;

import org.joda.time.DateTimeConstants;

public abstract class AbstractModifiedFollowingDateCalculatorTest<E> extends AbstractDateTestCase<E> {

    public void testSimpleForwardWithWeekend() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
        JodaWorkingWeek ww = new JodaWorkingWeek();
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SATURDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SUNDAY);
        cal.setWorkingWeek(ww);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        JodaWorkingWeek ww = new JodaWorkingWeek();
        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.MONDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.TUESDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.WEDNESDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.THURSDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.FRIDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SATURDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.SUNDAY);
        cal.setWorkingWeek(ww);

        cal.setStartDate(newDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-07-29");

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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        JodaWorkingWeek ww = new JodaWorkingWeek();
        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.MONDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.TUESDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.WEDNESDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.THURSDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.FRIDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.SATURDAY);
        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.SUNDAY);
        cal.setWorkingWeek(ww);

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
        checkDate("start date Saturday", cal, "2006-08-08");

        cal.setStartDate(newDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-08");
    }

    public void testSimpleForwardWithHolidays() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
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
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 1D", cal.moveByTenor(StandardTenor.T_1D), "2006-08-09");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 2D", cal.moveByTenor(new Tenor(2, TenorCode.DAY)), "2006-08-10");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 10D", cal.moveByTenor(new Tenor(10, TenorCode.DAY)), "2006-08-18");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 11D", cal.moveByTenor(new Tenor(11, TenorCode.DAY)), "2006-08-21");

    }

    public void testMoveByTenorWeek() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 1W", cal.moveByTenor(StandardTenor.T_1W), "2006-08-15");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 2W", cal.moveByTenor(new Tenor(2, TenorCode.WEEK)), "2006-08-22");

        cal.setStartDate(newDate("2006-08-08"));
        checkDate("Move 4W", cal.moveByTenor(new Tenor(4, TenorCode.WEEK)), "2006-09-05");
    }

    public void testAddAcrossMonth() {
        final DateCalculator<E> cal = newDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);

        cal.setStartDate(newDate("2006-07-28"));
        cal.moveByDays(1);
        checkDate("31/7", cal, "2006-07-31");

        cal.moveByDays(1);
        checkDate("do move to next month", cal, "2006-08-01");

        // now if it due to roll over:
        cal.setStartDate(newDate("2006-07-28"));
        final Set<E> holidays = newHolidaysSet();
        holidays.clear();
        holidays.add(newDate("2006-07-31"));
        cal.setNonWorkingDays(holidays);
        cal.moveByDays(1);

        checkDate("do NOT move to next month", cal, "2006-07-28");
    }
}
