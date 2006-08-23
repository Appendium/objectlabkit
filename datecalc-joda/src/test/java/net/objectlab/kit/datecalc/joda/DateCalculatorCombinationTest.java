package net.objectlab.kit.datecalc.joda;

import junit.framework.Assert;
import net.objectlab.kit.datecalc.common.DateCalculatorGeneric;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.LocalDate;

public class DateCalculatorCombinationTest extends AbstractDateCalculatorTest {

    public void testInvalidCombinationDiffHandler() {
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.BACKWARD);
        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2",
                HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNullNotNullHandler() {
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla", null);
        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2",
                HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNotNullNullHandler() {
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2", null);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testValidCombinationOneEmptySet() {
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculatorGeneric combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "bla/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination() {
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculatorGeneric combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "UK/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination2Sets() {
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator cal2 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculatorGeneric combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "US/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentDate());
        Assert.assertEquals("Holidays", 6, combo.getNonWorkingDays().size());
    }

    public void testNullCombination() {
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculatorGeneric combo = cal1.combine(null);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
    }

    public void testSameCombination() {
        DefaultDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator cal1 = DefaultDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculatorGeneric combo = cal1.combine(cal1);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
    }
}
