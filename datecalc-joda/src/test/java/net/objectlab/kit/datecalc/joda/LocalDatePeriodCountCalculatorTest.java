package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractPeriodCountCalculatorTest;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

import org.joda.time.LocalDate;

public class LocalDatePeriodCountCalculatorTest extends AbstractPeriodCountCalculatorTest<LocalDate> {

    @Override
    public PeriodCountCalculator<LocalDate> getPeriodCountCalculator() {
        return LocalDateKitCalculatorsFactory.getDefaultInstance().getPeriodCountCalculator();
    }

    @Override
    public LocalDate parseDate(final String string) {
        return new LocalDate(string);
    }

    @Override
    public LocalDate getDate() {
        return new LocalDate();
    }

}