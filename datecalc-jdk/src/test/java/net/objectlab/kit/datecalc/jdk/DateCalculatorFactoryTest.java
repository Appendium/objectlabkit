package net.objectlab.kit.datecalc.jdk;

import java.util.Date;
import java.util.Set;

import junit.framework.Assert;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

public class DateCalculatorFactoryTest extends AbstractDateCalculatorTest {

    public void testGetCalendarsNoHoliday() {
        final DateCalculator<Date> cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        Assert.assertNotNull("cal1", cal1);
        Assert.assertEquals("name", "bla", cal1.getName());
        Assert.assertTrue("no holiday", cal1.getNonWorkingDays().isEmpty());

        final DateCalculator<Date> cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        Assert.assertNotNull("cal2", cal2);
        Assert.assertEquals("name", "bla", cal2.getName());
        Assert.assertTrue("no holiday", cal2.getNonWorkingDays().isEmpty());
        Assert.assertNotSame(cal1, cal2);
    }

    public void testGetCalendarsNoHolidayButSomeRegistered() {
        final Set<Date> uk = createUKHolidays();
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", uk);

        final DateCalculator<Date> cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        Assert.assertNotNull("cal1", cal1);
        Assert.assertEquals("name", "bla", cal1.getName());
        Assert.assertTrue("no holiday", cal1.getNonWorkingDays().isEmpty());

        final DateCalculator<Date> cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK", null);
        Assert.assertNotNull("cal2", cal2);
        Assert.assertEquals("name cal2", "UK", cal2.getName());
        Assert.assertEquals("UK holidays", 4, cal2.getNonWorkingDays().size());
        Assert.assertNotSame(cal1, cal2);
    }

    public void testGetCorrectAlgo() {
        DateCalculator<Date> cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);

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
}
