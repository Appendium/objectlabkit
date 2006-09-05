package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

public class YearMonthDayIMMDateTest extends AbstractIMMDateTest<YearMonthDay> {

    @Override
    protected DateCalculator<YearMonthDay> getDateCalculator(final String name) {
        return DefaultYearMonthDayCalculatorFactory.getDefaultInstance().getDateCalculator(name, HolidayHandlerType.FORWARD);
    }

    @Override
    protected YearMonthDay parseDate(final String string) {
        return new YearMonthDay(string);
    }
}