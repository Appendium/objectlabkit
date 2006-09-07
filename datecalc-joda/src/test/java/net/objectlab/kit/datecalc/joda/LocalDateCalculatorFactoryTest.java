package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorFactoryTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;

import org.joda.time.LocalDate;

public class LocalDateCalculatorFactoryTest extends AbstractDateCalculatorFactoryTest<LocalDate> {

    @Override
    protected LocalDate newDate(final String date) {
        return new LocalDate(date);
    }

    @Override
    protected DateCalculatorFactory<LocalDate> getDateCalculatorFactory() {
        return DefaultLocalDateCalculatorFactory.getDefaultInstance();
    }
}
