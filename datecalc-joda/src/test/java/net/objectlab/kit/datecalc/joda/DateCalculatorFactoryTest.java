package net.objectlab.kit.datecalc.joda;

import java.util.Set;

import junit.framework.Assert;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.LocalDate;

public class DateCalculatorFactoryTest extends AbstractDateCalculatorTest {

    public void testGetCalendarsNoHoliday() {
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        Assert.assertNotNull("cal1", cal1);
        Assert.assertEquals("name", "bla", cal1.getName());
        Assert.assertTrue("no holiday", cal1.getNonWorkingDays().isEmpty());

        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        Assert.assertNotNull("cal2", cal2);
        Assert.assertEquals("name", "bla", cal2.getName());
        Assert.assertTrue("no holiday", cal2.getNonWorkingDays().isEmpty());
        Assert.assertNotSame(cal1, cal2);
    }

    public void testGetCalendarsNoHolidayButSomeRegistered() {
        final Set<LocalDate> uk = createUKHolidays();
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", uk);

        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        Assert.assertNotNull("cal1", cal1);
        Assert.assertEquals("name", "bla", cal1.getName());
        Assert.assertTrue("no holiday", cal1.getNonWorkingDays().isEmpty());

        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK", null);
        Assert.assertNotNull("cal2", cal2);
        Assert.assertEquals("name cal2", "UK", cal2.getName());
        Assert.assertEquals("UK holidays", 4, cal2.getNonWorkingDays().size());
        Assert.assertNotSame(cal1, cal2);
    }

    public void testGetCorrectAlgo() {
        DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);

        Assert.assertNull("No algo", cal1.getHolidayHandlerType());
        cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", HolidayHandlerType.BACKWARD);
        Assert.assertEquals("Type", HolidayHandlerType.BACKWARD, cal1.getHolidayHandlerType());

        cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", HolidayHandlerType.FORWARD);
        Assert.assertEquals("Type", HolidayHandlerType.FORWARD, cal1.getHolidayHandlerType());

        cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", HolidayHandlerType.MODIFIED_FOLLLOWING);
        Assert.assertEquals("Type", HolidayHandlerType.MODIFIED_FOLLLOWING, cal1.getHolidayHandlerType());

        cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", HolidayHandlerType.MODIFIED_PRECEEDING);
        Assert.assertEquals("Type", HolidayHandlerType.MODIFIED_PRECEEDING, cal1.getHolidayHandlerType());

    }

    public void testSetHol() {
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);

        Assert.assertNotNull("No algo", cal1);
        Assert.assertNotNull("No hol", cal1.getNonWorkingDays());
        Assert.assertTrue("empty hol", cal1.getNonWorkingDays().isEmpty());

        cal1.setNonWorkingDays(null);
        Assert.assertNotNull("empty", cal1.getNonWorkingDays());
        Assert.assertTrue("empty hol", cal1.getNonWorkingDays().isEmpty());
    }

    public void testUseDefault() {
        final DateCalculator<LocalDate> cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);

        Assert.assertNotNull("No algo", cal1);
        Assert.assertNotNull("No hol", cal1.getNonWorkingDays());
        Assert.assertTrue("empty hol", cal1.getNonWorkingDays().isEmpty());

        LocalDate date = cal1.moveByDays(0).getCurrentDate();
        Assert.assertEquals("default today", new LocalDate(), date);

        cal1.setStartDate(null);
        date = cal1.moveByDays(0).getCurrentDate();
        Assert.assertEquals("default today", new LocalDate(), date);

        cal1.setStartDate(new LocalDate("2006-08-08"));
        cal1.setCurrentBusinessDate(null);
        date = cal1.moveByDays(0).getCurrentDate();
        Assert.assertEquals("default today", new LocalDate(), date);
    }

    public void testHolNoAlgo() {
        final Set<LocalDate> uk = createUKHolidays();
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", uk);
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK", null);

        Assert.assertNotNull("No algo", cal1);
        Assert.assertNotNull("No hol", cal1.getNonWorkingDays());
        Assert.assertTrue("non empty hol", !cal1.getNonWorkingDays().isEmpty());

        cal1.setCurrentBusinessDate(new LocalDate("2006-12-25"));
        Assert.assertTrue("current date is holiday", cal1.isCurrentDateNonWorking());

        cal1.setCurrentBusinessDate(new LocalDate("2006-12-24"));
        Assert.assertTrue("current date is weekend", cal1.isCurrentDateNonWorking());

    }
}
