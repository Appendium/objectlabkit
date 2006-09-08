package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorFactoryTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;

import org.joda.time.LocalDate;

public class LocalDateCalculatorFactoryTest extends AbstractDateCalculatorFactoryTest<LocalDate> {

    @Override
    protected LocalDate newDate(final String date) {
        return new LocalDate(date);
    }

    @Override
    protected KitCalculatorsFactory<LocalDate> getDateCalculatorFactory() {
        return LocalDateKitCalculatorsFactory.getDefaultInstance();
    }
}
