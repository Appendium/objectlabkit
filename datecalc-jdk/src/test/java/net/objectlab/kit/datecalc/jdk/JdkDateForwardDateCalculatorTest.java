package net.objectlab.kit.datecalc.jdk;

import net.objectlab.kit.datecalc.common.AbstractForwardDateCalculatorTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateForwardDateCalculatorTest extends AbstractForwardDateCalculatorTest {

    @Override
    protected Object newDate(final String date) {
        return Utils.createDate(date);
    }

    @Override
    protected KitCalculatorsFactory getDateCalculatorFactory() {
        return DateKitCalculatorsFactory.getDefaultInstance();
    }

}
