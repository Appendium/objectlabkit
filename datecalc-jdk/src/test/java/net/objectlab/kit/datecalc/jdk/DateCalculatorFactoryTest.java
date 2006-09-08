package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorFactoryTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class DateCalculatorFactoryTest extends AbstractDateCalculatorFactoryTest<Date> {

    @Override
    protected Date newDate(final String date) {
        return Utils.createDate(date);
    }

    @Override
    protected KitCalculatorsFactory<Date> getDateCalculatorFactory() {
        return DateKitCalculatorsFactory.getDefaultInstance();
    }
}
