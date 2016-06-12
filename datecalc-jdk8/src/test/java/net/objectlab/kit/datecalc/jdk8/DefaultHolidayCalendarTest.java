package net.objectlab.kit.datecalc.jdk8;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import net.objectlab.kit.datecalc.common.DefaultHolidayCalendar;
import net.objectlab.kit.datecalc.common.HolidayCalendar;

public class DefaultHolidayCalendarTest extends TestCase {
    public void testGetHolidays() {
        final Set<LocalDate> holidays = new HashSet<>();
        holidays.add(LocalDate.parse("2009-04-22"));
        holidays.add(LocalDate.parse("2010-04-22"));

        final HolidayCalendar<LocalDate> holidayCalendar = new DefaultHolidayCalendar<>(holidays, LocalDate.parse("2009-01-01"),
                LocalDate.parse("2009-12-01"));
        assertTrue(holidayCalendar.getHolidays().size() == 2);
    }

    public void testIsHoliday() {
        final Set<LocalDate> holidays = new HashSet<>();
        final LocalDate holiday = LocalDate.parse("2009-04-22");
        holidays.add(holiday);

        final HolidayCalendar<LocalDate> holidayCalendar = new DefaultHolidayCalendar<>(holidays, LocalDate.parse("2009-01-01"),
                LocalDate.parse("2009-12-01"));

        final LocalDate testHoliday = LocalDate.parse("2009-04-22");
        assertTrue(holidayCalendar.isHoliday(testHoliday));

        assertFalse(holidayCalendar.isHoliday(LocalDate.parse("2009-04-21")));
    }
}
