package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.WorkingWeek;

public class ForwardDateCalculatorTest extends AbstractDateCalculatorTest {

    public void testSimpleForwardWithWeekend() {
        final JdkDateCalculator cal = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final Date startDate = createDate("2006-08-01");
        cal.setStartDate(startDate);
        checkDate("Move by 0 days", cal.moveByDays(0), "2006-08-01");
        checkDate("Move by 1 days", cal.moveByDays(1), "2006-08-02");
        checkDate("Move by 1 more days", cal.moveByDays(1), "2006-08-03");
        checkDate("Move by 1 more more days", cal.moveByDays(1), "2006-08-04");
        checkDate("Move by 1 more more more days (across weekend)", cal.moveByDays(1), "2006-08-07");
    }

    public void testSimpleForwardStartDateWithWeekend() {
        final JdkDateCalculator cal = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        cal.setStartDate(createDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-07-31");

        cal.setStartDate(createDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(createDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-02");

        cal.setStartDate(createDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(createDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-04");

        cal.setStartDate(createDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-07");

        cal.setStartDate(createDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-07");
    }

    public void testSimpleForwardStartDateNoWeekend() {
        final JdkDateCalculator cal = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final WorkingWeek ww = new WorkingWeek();
        ww.withWorkingDayFromCalendar(true, Calendar.SATURDAY);
        ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY);
        cal.setWorkingWeek(ww);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        cal.setStartDate(createDate("2006-07-31"));
        checkDate("start date Monday", cal, "2006-07-31");

        cal.setStartDate(createDate("2006-08-01"));
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(createDate("2006-08-02"));
        checkDate("start date Wednesday", cal, "2006-08-02");

        cal.setStartDate(createDate("2006-08-03"));
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(createDate("2006-08-04"));
        checkDate("start date friday", cal, "2006-08-04");

        cal.setStartDate(createDate("2006-08-05"));
        checkDate("start date Saturday, move to Monday", cal, "2006-08-07");

        cal.setStartDate(createDate("2006-08-06"));
        checkDate("start date Sunday, move to Monday", cal, "2006-08-07");
    }

    public void testSimpleForwardStartDateWhackyWeek() {
        final JdkDateCalculator cal = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final WorkingWeek ww = new WorkingWeek()
            .withWorkingDayFromCalendar(false, Calendar.MONDAY)
            .withWorkingDayFromCalendar(true, Calendar.TUESDAY)
            .withWorkingDayFromCalendar(false, Calendar.WEDNESDAY)
            .withWorkingDayFromCalendar(true, Calendar.THURSDAY)
            .withWorkingDayFromCalendar(false, Calendar.FRIDAY)
            .withWorkingDayFromCalendar(true, Calendar.SATURDAY)
            .withWorkingDayFromCalendar(false, Calendar.SUNDAY);
        cal.setWorkingWeek(ww);

        cal.setStartDate(createDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-08-01");

        cal.setStartDate(createDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(createDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-03");

        cal.setStartDate(createDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(createDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-05");

        cal.setStartDate(createDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-05");

        cal.setStartDate(createDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-08");
    }

    public void testSimpleForwardStartDateIdealWeekend() {
        final JdkDateCalculator cal = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final WorkingWeek ww = new WorkingWeek()
                .withWorkingDayFromCalendar(false, Calendar.MONDAY)
                .withWorkingDayFromCalendar(true, Calendar.TUESDAY)
                .withWorkingDayFromCalendar(true, Calendar.WEDNESDAY)
                .withWorkingDayFromCalendar(true, Calendar.THURSDAY)
                .withWorkingDayFromCalendar(true, Calendar.FRIDAY)
                .withWorkingDayFromCalendar(false, Calendar.SATURDAY)
                .withWorkingDayFromCalendar(false, Calendar.SUNDAY);
        cal.setWorkingWeek(ww);

        cal.setStartDate(createDate("2006-07-31")); // start date Monday
        checkDate("start date Monday", cal, "2006-08-01");

        cal.setStartDate(createDate("2006-08-01")); // start date Tuesday
        checkDate("start date Tuesday", cal, "2006-08-01");

        cal.setStartDate(createDate("2006-08-02")); // start date Wednesday
        checkDate("start date Wednesday", cal, "2006-08-02");

        cal.setStartDate(createDate("2006-08-03")); // start date Thursday
        checkDate("start date Thursday", cal, "2006-08-03");

        cal.setStartDate(createDate("2006-08-04")); // set on a Friday
        checkDate("start date friday", cal, "2006-08-04");

        cal.setStartDate(createDate("2006-08-05")); // set on a Saturday
        checkDate("start date Saturday", cal, "2006-08-08");

        cal.setStartDate(createDate("2006-08-06")); // set on a Sunday
        checkDate("start date Sunday", cal, "2006-08-08");
    }

    public void testSimpleForwardWithHolidays() {
        final JdkDateCalculator cal = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final Set<Date> holidays = new HashSet<Date>();
        holidays.add(createDate("2006-08-28"));
        holidays.add(createDate("2006-12-25"));
        holidays.add(createDate("2006-12-26"));
        Assert.assertEquals("Name", "bla", cal.getName());
        cal.setNonWorkingDays(holidays);
        Assert.assertEquals("Holidays", holidays, cal.getNonWorkingDays());
        Assert.assertEquals("Holidays size", 3, cal.getNonWorkingDays().size());

        Assert.assertTrue("contains", holidays.contains(createDate("2006-08-28")));
        Assert.assertTrue("contains", cal.getNonWorkingDays().contains(createDate("2006-08-28")));

        cal.setStartDate(createDate("2006-08-28"));
        checkDate("Move given Bank Holiday", cal, "2006-08-29");

        cal.setStartDate(createDate("2006-12-24"));
        checkDate("Xmas Eve", cal, "2006-12-27");

        cal.setStartDate(createDate("2006-12-21"));
        checkDate("21/12 + 1", cal.moveByDays(1), "2006-12-22");

        cal.setStartDate(createDate("2006-12-21"));
        checkDate("21/12 + 1", cal.moveByDays(2), "2006-12-27");

        cal.setStartDate(createDate("2006-12-22"));
        checkDate("22/12 + 1", cal.moveByDays(1), "2006-12-27");

        cal.setStartDate(createDate("2006-12-23"));
        checkDate("23/12 + 1", cal.moveByDays(1), "2006-12-28");
    }

    private void checkDate(final String string, final JdkDateCalculator calendar, final String string2) {
        Assert.assertEquals(string, createDate(string2), calendar.getCurrentBusinessDate());
    }
}
