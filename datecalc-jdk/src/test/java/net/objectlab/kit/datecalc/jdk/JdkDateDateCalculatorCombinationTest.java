package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorCombinationTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateDateCalculatorCombinationTest extends AbstractDateCalculatorCombinationTest<Date> {

    @Override
    protected Date newDate(final String date) {
        return Utils.createDate(date);
    }

    @Override
    protected KitCalculatorsFactory<Date> getDateCalculatorFactory() {
        return DateKitCalculatorsFactory.getDefaultInstance();
    }

}
