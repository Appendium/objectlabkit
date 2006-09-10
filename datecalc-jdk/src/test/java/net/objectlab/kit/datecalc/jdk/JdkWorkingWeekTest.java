package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.objectlab.kit.datecalc.common.WorkingWeek;

public class JdkWorkingWeekTest extends TestCase {

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

    public void testSetWorkingDayFromCalendar() {
        final WorkingWeek ww = new WorkingWeek();
        ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working
        // day
        ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working
        // day
        // do
        // it
        // twice
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww.withWorkingDayFromCalendar(false, Calendar.SUNDAY); // sunday
        // working
        // day
        Assert.assertTrue("2/ Calendar.MONDAY", ww.isWorkingDayFromCalendar(Calendar.MONDAY));
        Assert.assertTrue("2/ Calendar.TUESDAY", ww.isWorkingDayFromCalendar(Calendar.TUESDAY));
        Assert.assertTrue("2/ Calendar.WEDNESDAY", ww.isWorkingDayFromCalendar(Calendar.WEDNESDAY));
        Assert.assertTrue("2/ Calendar.THURSDAY", ww.isWorkingDayFromCalendar(Calendar.THURSDAY));
        Assert.assertTrue("2/ Calendar.FRIDAY", ww.isWorkingDayFromCalendar(Calendar.FRIDAY));
        Assert.assertFalse("2/ Calendar.SATURDAY", ww.isWorkingDayFromCalendar(Calendar.SATURDAY));
        Assert.assertFalse("2/ Calendar.SUNDAY", ww.isWorkingDayFromCalendar(Calendar.SUNDAY));

        ww.withWorkingDayFromCalendar(true, Calendar.SUNDAY); // sunday
        // working
        // day
        // do
        // it
        // twice
    }

}
