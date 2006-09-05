package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;

import org.joda.time.LocalDate;

public class LocalDateIMMDateTest extends AbstractIMMDateTest<LocalDate> {

    @Override
    protected DateCalculator<LocalDate> getDateCalculator(final String name) {
        return DefaultLocalDateCalculatorFactory.getDefaultInstance().getDateCalculator(name, HolidayHandlerType.FORWARD);
    }

    @Override
    protected LocalDate parseDate(final String string) {
        return new LocalDate(string);
    }
}