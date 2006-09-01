package net.objectlab.kit.datecalc.joda;

import junit.framework.Assert;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.LocalDate;

public class DateCalculatorCombinationTest extends AbstractDateCalculatorTest {

    public void testInvalidCombinationDiffHandler() {
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.BACKWARD);
        final DateCalculator<LocalDate> cal2 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2",
                HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNullNotNullHandler() {
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                null);
        final DateCalculator<LocalDate> cal2 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2",
                HolidayHandlerType.FORWARD);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testInvalidCombinationNotNullNullHandler() {
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final DateCalculator<LocalDate> cal2 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla2",
                null);

        try {
            cal1.combine(cal2);
            Assert.fail("should not have allowed 2 different handler types to be combined");
        } catch (final IllegalArgumentException e) {
            // ok
        }
    }

    public void testValidCombinationOneEmptySet() {
        DefaultLocalDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("bla",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<LocalDate> cal2 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculator<LocalDate> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "bla/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination() {
        DefaultLocalDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        DefaultLocalDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<LocalDate> cal2 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculator<LocalDate> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "UK/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 4, combo.getNonWorkingDays().size());
    }

    public void testValidCombination2Sets() {
        DefaultLocalDateCalculatorFactory.getDefaultInstance().registerHolidays("UK", createUKHolidays());
        DefaultLocalDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);
        final DateCalculator<LocalDate> cal2 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("UK",
                HolidayHandlerType.FORWARD);

        final DateCalculator<LocalDate> combo = cal1.combine(cal2);
        Assert.assertEquals("Combo name", "US/UK", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 6, combo.getNonWorkingDays().size());
    }

    public void testNullCombination() {
        DefaultLocalDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<LocalDate> combo = cal1.combine(null);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
    }

    public void testSameCombination() {
        DefaultLocalDateCalculatorFactory.getDefaultInstance().registerHolidays("US", createUSHolidays());
        final DateCalculator<LocalDate> cal1 = DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator("US",
                HolidayHandlerType.FORWARD);
        final LocalDate localDate = new LocalDate("2006-08-08");
        cal1.setStartDate(localDate);

        final DateCalculator<LocalDate> combo = cal1.combine(cal1);
        Assert.assertSame("same", combo, cal1);
        Assert.assertEquals("Combo name", "US", combo.getName());
        Assert.assertEquals("Combo type", HolidayHandlerType.FORWARD, combo.getHolidayHandlerType());
        Assert.assertEquals("start", localDate, combo.getStartDate());
        Assert.assertEquals("currentDate", localDate, combo.getCurrentBusinessDate());
        Assert.assertEquals("Holidays", 3, combo.getNonWorkingDays().size());
    }
}
