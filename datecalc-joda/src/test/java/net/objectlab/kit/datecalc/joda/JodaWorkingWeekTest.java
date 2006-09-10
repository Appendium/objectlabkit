package net.objectlab.kit.datecalc.joda;

import java.util.Calendar;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

public class JodaWorkingWeekTest extends TestCase {

    private JodaWorkingWeek ww;

    @Override
    public void setUp() {
        ww = new JodaWorkingWeek();
    }

    public void testIsWorkingDayFromDateTimeConstant() {
        Assert.assertTrue("DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.MONDAY));
        Assert.assertTrue("DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.TUESDAY));
        Assert.assertTrue("DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.WEDNESDAY));
        Assert.assertTrue("DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.THURSDAY));
        Assert.assertTrue("DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.FRIDAY));
        Assert.assertFalse("DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertFalse("DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));
    }

    public void testIsWorkingDay() {
        LocalDate date = new LocalDate("2006-08-01");
        Assert.assertTrue("Calendar.TUESDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.WEDNESDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.THURSDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.FRIDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertFalse("Calendar.SATURDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertFalse("Calendar.SUNDAY", ww.isWorkingDay(date));
        date = date.plusDays(1);
        Assert.assertTrue("Calendar.MONDAY", ww.isWorkingDay(date));
    }

    public void testSetWorkingDayFromDateTimeConstant() {
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SUNDAY); // sunday
        // working
        // day
        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SUNDAY); // sunday
        // working
        // day
        // do
        // it
        // twice
        Assert.assertTrue("DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.MONDAY));
        Assert.assertTrue("DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.TUESDAY));
        Assert.assertTrue("DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.WEDNESDAY));
        Assert.assertTrue("DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.THURSDAY));
        Assert.assertTrue("DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.FRIDAY));
        Assert.assertFalse("DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertTrue("DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));

        ww = ww.withWorkingDayFromDateTimeConstant(false, DateTimeConstants.SUNDAY); // sunday
        // working
        // day
        Assert.assertTrue("2/ DateTimeConstants.MONDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.MONDAY));
        Assert.assertTrue("2/ DateTimeConstants.TUESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.TUESDAY));
        Assert.assertTrue("2/ DateTimeConstants.WEDNESDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.WEDNESDAY));
        Assert.assertTrue("2/ DateTimeConstants.THURSDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.THURSDAY));
        Assert.assertTrue("2/ DateTimeConstants.FRIDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.FRIDAY));
        Assert.assertFalse("2/ DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertFalse("2/ DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));

        ww = ww.withWorkingDayFromDateTimeConstant(true, DateTimeConstants.SUNDAY); // sunday
        // working
        // day
        // do
        // it
        // twice
        Assert.assertFalse("2/ DateTimeConstants.SATURDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SATURDAY));
        Assert.assertTrue("2/ DateTimeConstants.SUNDAY", ww.isWorkingDayFromDateTimeConstant(DateTimeConstants.SUNDAY));

    }

    public void testJodaToCalendarDayConstant() {
        assertEquals("Monday", ww.jodaToCalendarDayConstant(DateTimeConstants.MONDAY), Calendar.MONDAY);
        assertEquals("Tuesday", ww.jodaToCalendarDayConstant(DateTimeConstants.TUESDAY), Calendar.TUESDAY);
        assertEquals("Wednesday", ww.jodaToCalendarDayConstant(DateTimeConstants.WEDNESDAY), Calendar.WEDNESDAY);
        assertEquals("Thursday", ww.jodaToCalendarDayConstant(DateTimeConstants.THURSDAY), Calendar.THURSDAY);
        assertEquals("Friday", ww.jodaToCalendarDayConstant(DateTimeConstants.FRIDAY), Calendar.FRIDAY);
        assertEquals("Saturday", ww.jodaToCalendarDayConstant(DateTimeConstants.SATURDAY), Calendar.SATURDAY);
        assertEquals("Sunday", ww.jodaToCalendarDayConstant(DateTimeConstants.SUNDAY), Calendar.SUNDAY);

    }

}
