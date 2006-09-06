package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;

import org.joda.time.YearMonthDay;

public class YearMonthDayIMMDateTest extends AbstractIMMDateTest<YearMonthDay> {

    @Override
    protected IMMDateCalculator<YearMonthDay> getDateCalculator(final String name) {
        return DefaultYearMonthDayCalculatorFactory.getDefaultInstance().getIMMDateCalculator();
    }

    @Override
    protected YearMonthDay parseDate(final String string) {
        return new YearMonthDay(string);
    }
}