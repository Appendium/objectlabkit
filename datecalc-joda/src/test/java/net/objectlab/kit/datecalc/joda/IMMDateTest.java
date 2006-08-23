package net.objectlab.kit.datecalc.joda;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.StandardTenor;

import org.joda.time.LocalDate;

public class IMMDateTest extends TestCase {

    public void testNextIMM() {
        final DateCalculator<LocalDate> cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2006-08-01");
        cal.setStartDate(startDate);

        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-01-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-02-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-03-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-04-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-05-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-06-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-07-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-08-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-09-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-10-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-11-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-12-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-12-20"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-03-14"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-03-15"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-03-16"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-06-20"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-06-21"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-06-22"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-09-19"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-09-20"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-09-21"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-12-20"), cal.getNextIMMDate());

        cal.setStartDate(new LocalDate("2006-12-19"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-12-20"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2007-03-21"), cal.getNextIMMDate());
        cal.setStartDate(new LocalDate("2006-12-21"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2007-03-21"), cal.getNextIMMDate());
    }

    public void testMoveByIMMTenor() {
        final DateCalculator cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2006-04-01");
        cal.setStartDate(startDate);

        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.moveByTenor(StandardTenor.IMM)
                .getCurrentDate());

    }

    public void testMoveByNullTenor() {
        final DateCalculator cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2006-04-01");
        cal.setStartDate(startDate);

        try {
            cal.moveByTenor(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testPreviousIMM() {
        final DateCalculator cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2006-08-01");
        cal.setStartDate(startDate);

        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getPreviousIMMDate());

        cal.setStartDate(new LocalDate("2006-01-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-02-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-03-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2005-12-21"), cal.getPreviousIMMDate());

        cal.setStartDate(new LocalDate("2006-04-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-05-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-06-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getPreviousIMMDate());

        cal.setStartDate(new LocalDate("2006-07-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-08-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-09-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getPreviousIMMDate());

        cal.setStartDate(new LocalDate("2006-10-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-11-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-12-09"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getPreviousIMMDate());

        // close to dates

        cal.setStartDate(new LocalDate("2006-03-14"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-03-15"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-03-16"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getPreviousIMMDate());

        cal.setStartDate(new LocalDate("2006-06-20"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-06-21"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-06-22"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getPreviousIMMDate());

        cal.setStartDate(new LocalDate("2006-09-19"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-09-20"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-09-21"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getPreviousIMMDate());

        cal.setStartDate(new LocalDate("2006-12-19"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-12-20"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(new LocalDate("2006-12-21"));
        Assert.assertEquals("From " + cal.getStartDate(), new LocalDate("2006-12-20"), cal.getPreviousIMMDate());
    }

    public void testIMMLists() {
        final DateCalculator cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2005-12-01");
        final LocalDate endDate = new LocalDate("2007-03-17");
        final List<LocalDate> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("Not empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 5, imms.size());
        Assert.assertEquals("date 1", new LocalDate("2005-12-21"), imms.get(0));
        Assert.assertEquals("date 2", new LocalDate("2006-03-15"), imms.get(1));
        Assert.assertEquals("date 3", new LocalDate("2006-06-21"), imms.get(2));
        Assert.assertEquals("date 4", new LocalDate("2006-09-20"), imms.get(3));
        Assert.assertEquals("date 5", new LocalDate("2006-12-20"), imms.get(4));
    }

    public void testEmptyIMMLists() {
        final DateCalculator cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2006-03-16");
        final LocalDate endDate = new LocalDate("2006-06-20");
        final List<LocalDate> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", imms.isEmpty());
    }

    public void testEndOnIMMDateIMMLists() {
        final DateCalculator cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2006-03-16");
        final LocalDate endDate = new LocalDate("2006-06-21");
        final List<LocalDate> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 1, imms.size());
        Assert.assertEquals("date 1", new LocalDate("2006-06-21"), imms.get(0));
    }

    public void testStartOnIMMDateIMMLists() {
        final DateCalculator cal = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());

        final LocalDate startDate = new LocalDate("2006-03-15");
        final LocalDate endDate = new LocalDate("2006-06-20");
        final List<LocalDate> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", imms.isEmpty());
    }
}