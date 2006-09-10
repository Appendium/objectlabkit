package net.objectlab.kit.datecalc.common;

import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

public class WorkingWeekTest extends TestCase {

    public void testIsWorkingDayFromCalendar() {
        final WorkingWeek ww = new WorkingWeek();
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));
    }

    public void testIsWorkingDay() {
        final WorkingWeek ww = new WorkingWeek();
        final Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, Calendar.AUGUST);
        cal.set(Calendar.YEAR, 2006);
        Date date = cal.getTime();
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDay(date));
        cal.add(Calendar.DATE, 1);
        date = cal.getTime();
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDay(date));
    }

    public void testSetWorkingDayFromCalendar() {
        WorkingWeek ww = new WorkingWeek();
        ww = ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working day
        ww = ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working day
        // do it twice
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertTrue("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww = ww.withWorkingDayFromCalendar(false, Calendar.SUNDAY); // sunday
        // working day
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww = ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working day
        // do it twice
    }
}
