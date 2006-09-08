package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorFactoryTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;

import org.joda.time.YearMonthDay;

public class YearMonthDayCalculatorFactoryTest extends AbstractDateCalculatorFactoryTest<YearMonthDay> {

    @Override
    protected YearMonthDay newDate(final String date) {
        return new YearMonthDay(date);
    }

    @Override
    protected KitCalculatorsFactory<YearMonthDay> getDateCalculatorFactory() {
        return YearMonthDayKitCalculatorsFactory.getDefaultInstance();
    }
}
