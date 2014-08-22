package net.objectlab.kit.datecalc.joda;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DefaultHolidayCalendarTest extends TestCase {

    public void testGetHolidays() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        holidays.add(new LocalDate("2009-04-22"));
        holidays.add(new LocalDate("2010-04-22"));

        final HolidayCalendar<LocalDate> holidayCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2009-01-01"),
                new LocalDate("2009-12-01"));
        assertTrue(holidayCalendar.getHolidays().size() == 2);
    }

    public void testIsHoliday() {
        final Set<LocalDate> holidays = new HashSet<LocalDate>();
        final LocalDate holiday = new LocalDate("2009-04-22");
        holidays.add(holiday);

        final HolidayCalendar<LocalDate> holidayCalendar = new DefaultHolidayCalendar<LocalDate>(holidays, new LocalDate("2009-01-01"),
                new LocalDate("2009-12-01"));

        final LocalDate testHoliday = new LocalDate(2009, 4, 22);
        assertTrue(holidayCalendar.isHoliday(testHoliday));

        assertFalse(holidayCalendar.isHoliday(new LocalDate(2009, 4, 21)));
    }

    /**
     * See JODA issue:
     * http://joda-interest.219941.n2.nabble.com/LocalDate-equals-method-bug-td7572429.html
     */
    public void testForDateWithDifferentChronologies() {
        final LocalDate localDate2 = new LocalDate(2012, 6, 21);
        final Set<LocalDate> s = new HashSet<LocalDate>();
        s.add(localDate2);
        final HolidayCalendar<LocalDate> holidayCalendar = new DefaultHolidayCalendar<LocalDate>(s, new LocalDate("2009-01-01"), new LocalDate(
                "2009-12-01"));
        assertTrue("Date with Chronology " + localDate2.getChronology(), holidayCalendar.isHoliday(localDate2));

        final DateTimeFormatter dateTimeFormat = DateTimeFormat.forPattern("yyyyMMdd HH:mm");
        final Calendar calendar = dateTimeFormat.parseDateTime("20120621 09:00").toCalendar(null);
        final LocalDate localDate1 = new LocalDate(calendar);
        assertTrue("Date with Chronology " + localDate1.getChronology(), holidayCalendar.isHoliday(localDate1));
    }
}
