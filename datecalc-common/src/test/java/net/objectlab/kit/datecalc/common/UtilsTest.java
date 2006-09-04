package net.objectlab.kit.datecalc.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import junit.framework.TestCase;

public class UtilsTest extends TestCase {

    SimpleDateFormat sdf;

    Calendar cal;

    Set<Calendar> calendarSet;

    Set<Date> dateSet;

    protected void setUp() throws Exception {
        sdf = new SimpleDateFormat("yyyy-MM-dd");

        calendarSet = new HashSet<Calendar>();
        calendarSet.add(getCal(2004, 5, 31));
        calendarSet.add(getCal(2083, 12, 1));

        dateSet = new HashSet<Date>();
        dateSet.add(getCal(2004, 5, 31).getTime());
        dateSet.add(getCal(2083, 12, 1).getTime());
    }

    private Calendar getCal(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(year, month - 1, day, 0, 0, 0);
        return cal;
    }

    public void testGetCal() throws Exception {

        Date d = sdf.parse("2004-02-03");
        assertEquals(Utils.getCal(d).getTime(), getCal(2004, 2, 3).getTime());

        Date d1 = getCal(2080, 5, 31).getTime();
        Date d2 = getCal(2080, 5, 31).getTime();
        assertEquals(Utils.getCal(d1).getTime(), d2);
    }

    public void testCreateDate() throws Exception {

        assertEquals(getCal(1970, 1, 1).getTime(), Utils.createDate("1970-01-01"));
        assertEquals(getCal(2020, 12, 31).getTime(), Utils.createDate("2020-12-31"));

        assertEquals(getCal(2006, 8, 8).getTime(), Utils.createDate("2006-08-08"));
        assertEquals(getCal(2004, 9, 12).getTime(), Utils.createDate("2004-09-12"));
    }

    public void testToCalendarSet() throws Exception {
        assertEquals(calendarSet, Utils.toCalendarSet(dateSet));
    }

    public void testToDateSet() {
        assertEquals(dateSet, Utils.toDateSet(calendarSet));
    }

    public void testToDateList() {

        List<Date> expected = new ArrayList<Date>(dateSet);
        Collections.sort(expected);

        List<Date> actual = Utils.toDateList(new ArrayList<Calendar>(calendarSet));
        Collections.sort(actual);
        assertEquals(expected, actual);
    }
}
