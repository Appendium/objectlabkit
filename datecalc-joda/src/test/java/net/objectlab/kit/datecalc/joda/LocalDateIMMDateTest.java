package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;

import org.joda.time.LocalDate;

public class LocalDateIMMDateTest extends AbstractIMMDateTest<LocalDate> {

    @Override
    protected IMMDateCalculator<LocalDate> getDateCalculator(final String name) {
        return DefaultLocalDateCalculatorFactory.getDefaultInstance().getIMMDateCalculator();
    }

    @Override
    protected LocalDate parseDate(final String string) {
        return new LocalDate(string);
    }
}