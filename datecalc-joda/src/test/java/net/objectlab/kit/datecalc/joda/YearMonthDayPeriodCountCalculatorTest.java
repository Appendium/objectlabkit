package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractPeriodCountCalculatorTest;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

import org.joda.time.YearMonthDay;

public class YearMonthDayPeriodCountCalculatorTest extends AbstractPeriodCountCalculatorTest<YearMonthDay> {

    @Override
    public PeriodCountCalculator<YearMonthDay> getPeriodCountCalculator() {
        return YearMonthDayKitCalculatorsFactory.getDefaultInstance().getPeriodCountCalculator();
    }

    @Override
    public YearMonthDay parseDate(final String string) {
        return new YearMonthDay(string);
    }

    @Override
    public YearMonthDay getDate() {
        return new YearMonthDay();
    }

}