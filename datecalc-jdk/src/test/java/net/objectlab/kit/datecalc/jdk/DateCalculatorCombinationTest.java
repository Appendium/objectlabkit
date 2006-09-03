package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import junit.framework.Assert;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

public class DateCalculatorCombinationTest extends AbstractDateCalculatorTest {

    public void testInvalidCombinationDiffHandler() {
        final JdkDateCalculator cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.BACKWARD);
        final JdkDateCalculator cal2 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2",
                HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNullNotNullHandler() {
        final JdkDateCalculator cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        final DateCalculator<Date> cal2 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2",
                HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNotNullNullHandler() {
        final DateCalculator<Date> cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final DateCalculator<Date> cal2 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2", null);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testValidCombinationOneEmptySet() {
        DefaultJdkDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        final DateCalculator<Date> cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final Date Date = createDate("2006-08-08");
        cal1.setStartDate(Date);
        final DateCalculator<Date> cal2 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculator<Date> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "bla/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", Date, combo.getStartDate());
        Assert.assertEquals("currentDate", Date, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination() {
        DefaultJdkDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        DefaultJdkDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        final DateCalculator<Date> cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);
        final Date Date = createDate("2006-08-08");
        cal1.setStartDate(Date);
        final DateCalculator<Date> cal2 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculator<Date> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "UK/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", Date, combo.getStartDate());
        Assert.assertEquals("currentDate", Date, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination2Sets() {
        DefaultJdkDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        DefaultJdkDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator<Date> cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final Date Date = createDate("2006-08-08");
        cal1.setStartDate(Date);
        final DateCalculator<Date> cal2 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculator<Date> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "US/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", Date, combo.getStartDate());
        Assert.assertEquals("currentDate", Date, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 6, combo.getNonWorkingDays().size());
    }

    public void testNullCombination() {
        DefaultJdkDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator<Date> cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final Date Date = createDate("2006-08-08");
        cal1.setStartDate(Date);

        final DateCalculator<Date> combo = cal1.combine(null);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", Date, combo.getStartDate());
        Assert.assertEquals("currentDate", Date, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
    }

    public void testSameCombination() {
        DefaultJdkDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator<Date> cal1 = DefaultJdkDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final Date Date = createDate("2006-08-08");
        cal1.setStartDate(Date);

        final DateCalculator<Date> combo = cal1.combine(cal1);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", Date, combo.getStartDate());
        Assert.assertEquals("currentDate", Date, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
    }
}
