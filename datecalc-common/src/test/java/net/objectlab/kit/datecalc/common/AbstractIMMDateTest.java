package net.objectlab.kit.datecalc.common;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public abstract class AbstractIMMDateTest<E> extends TestCase {

    private DateCalculator<E> cal;

    public void setUp() {
        cal = getDateCalculator("bla");
    }

    protected abstract DateCalculator<E> getDateCalculator(String name);

    protected abstract E parseDate(String string);

    private void checkImm(final DateCalculator<E> cal, final E date, final boolean expected) {
        assertEquals("check " + date, expected, cal.isIMMDate(date));
    }

    public void testSanityCheck() {
        Assert.assertEquals("Name", "bla", cal.getName());
        Assert.assertEquals("Holidays size", 0, cal.getNonWorkingDays().size());
    }

    public void testNextIMM() {
        final E startDate = parseDate("2006-08-01");
        cal.setStartDate(startDate);
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-01-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-02-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-03-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-04-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-05-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-06-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-07-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-08-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-09-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-10-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-11-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-12-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-12-20"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-03-14"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-03-15"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-03-16"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-06-20"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-06-21"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-06-22"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-09-19"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-09-20"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-09-21"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-12-20"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-12-19"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-12-20"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-12-20"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2007-03-21"), cal.getNextIMMDate());
        cal.setStartDate(parseDate("2006-12-21"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2007-03-21"), cal.getNextIMMDate());

        cal.setStartDate(parseDate("2006-03-15"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getNextIMMDate());
    }

    public void testNextIMMWithPeriod() {
        final E startDate = parseDate("2006-08-01");
        cal.setStartDate(startDate);

        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getNextIMMDate());

        IMMPeriod period = IMMPeriod.QUARTERLY;

        cal.setStartDate(parseDate("2006-01-09"));
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-03-15"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.BI_ANNUALY_JUN_DEC;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-06-21"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.BI_ANNUALY_MAR_SEP;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-03-15"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.ANNUALLY;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2007-03-21"), cal
                .getNextIMMDate(period));

        cal.setStartDate(parseDate("2006-03-20"));
        period = IMMPeriod.QUARTERLY;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-06-21"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.BI_ANNUALY_JUN_DEC;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-06-21"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.BI_ANNUALY_MAR_SEP;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-09-20"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.ANNUALLY;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2007-06-20"), cal
                .getNextIMMDate(period));

        cal.setStartDate(parseDate("2006-03-15"));
        period = IMMPeriod.QUARTERLY;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-06-21"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.BI_ANNUALY_JUN_DEC;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-06-21"), cal
                .getNextIMMDate(period));
        period = IMMPeriod.BI_ANNUALY_MAR_SEP;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2006-09-20"), cal
                .getNextIMMDate(period));
        cal.setStartDate(parseDate("2006-03-15"));
        period = IMMPeriod.ANNUALLY;
        Assert.assertEquals("From " + cal.getStartDate() + " period:" + period, parseDate("2007-06-20"), cal
                .getNextIMMDate(period));
    }

    public void testIfIMMDate() {
        final E startDate = parseDate("2006-08-01");
        cal.setStartDate(startDate);

        checkImm(cal, parseDate("2006-08-01"), false);

        checkImm(cal, parseDate("2006-03-14"), false);
        checkImm(cal, parseDate("2006-03-15"), true);
        checkImm(cal, parseDate("2006-03-16"), false);

        checkImm(cal, parseDate("2006-06-20"), false);
        checkImm(cal, parseDate("2006-06-21"), true);
        checkImm(cal, parseDate("2006-06-22"), false);

        checkImm(cal, parseDate("2006-09-19"), false);
        checkImm(cal, parseDate("2006-09-20"), true);
        checkImm(cal, parseDate("2006-09-21"), false);

        checkImm(cal, parseDate("2006-12-19"), false);
        checkImm(cal, parseDate("2006-12-20"), true);
        checkImm(cal, parseDate("2006-12-21"), false);
    }

    public void testMoveByIMMTenor() {
        final E startDate = parseDate("2006-04-01");
        cal.setStartDate(startDate);

        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.moveByTenor(StandardTenor.IMM)
                .getCurrentBusinessDate());
    }

    public void testMoveByNullTenor() {
        final E startDate = parseDate("2006-04-01");
        cal.setStartDate(startDate);

        try {
            cal.moveByTenor(null);
            Assert.fail("Should have thrown IllegalArgumentException");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testPreviousIMM() {
        final E startDate = parseDate("2006-08-01");
        cal.setStartDate(startDate);

        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getPreviousIMMDate());

        cal.setStartDate(parseDate("2006-01-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-02-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-03-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2005-12-21"), cal.getPreviousIMMDate());

        cal.setStartDate(parseDate("2006-04-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-05-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-06-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getPreviousIMMDate());

        cal.setStartDate(parseDate("2006-07-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-08-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-09-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getPreviousIMMDate());

        cal.setStartDate(parseDate("2006-10-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-11-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-12-09"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getPreviousIMMDate());

        // close to dates

        cal.setStartDate(parseDate("2006-03-14"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-03-15"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2005-12-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-03-16"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getPreviousIMMDate());

        cal.setStartDate(parseDate("2006-06-20"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-06-21"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-03-15"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-06-22"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getPreviousIMMDate());

        cal.setStartDate(parseDate("2006-09-19"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-09-20"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-06-21"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-09-21"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getPreviousIMMDate());

        cal.setStartDate(parseDate("2006-12-19"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-12-20"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-09-20"), cal.getPreviousIMMDate());
        cal.setStartDate(parseDate("2006-12-21"));
        Assert.assertEquals("From " + cal.getStartDate(), parseDate("2006-12-20"), cal.getPreviousIMMDate());
    }

    public void testIMMLists() {
        final E startDate = parseDate("2005-12-01");
        final E endDate = parseDate("2007-03-17");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("Not empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 5, imms.size());
        Assert.assertEquals("date 1", parseDate("2005-12-21"), imms.get(0));
        Assert.assertEquals("date 2", parseDate("2006-03-15"), imms.get(1));
        Assert.assertEquals("date 3", parseDate("2006-06-21"), imms.get(2));
        Assert.assertEquals("date 4", parseDate("2006-09-20"), imms.get(3));
        Assert.assertEquals("date 5", parseDate("2006-12-20"), imms.get(4));
    }

    public void testEmptyIMMLists() {
        final E startDate = parseDate("2006-03-16");
        final E endDate = parseDate("2006-06-20");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", imms.isEmpty());
    }

    public void testEndOnIMMDateIMMLists() {
        final E startDate = parseDate("2006-03-16");
        final E endDate = parseDate("2006-06-21");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 1, imms.size());
        Assert.assertEquals("date 1", parseDate("2006-06-21"), imms.get(0));
    }

    public void testStartOnIMMDateIMMLists() {
        final E startDate = parseDate("2006-03-15");
        final E endDate = parseDate("2006-06-20");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", imms.isEmpty());
    }

}
