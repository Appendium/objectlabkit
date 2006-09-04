package net.objectlab.kit.datecalc.jdk;

import net.objectlab.kit.datecalc.common.AbstractBackwardDateCalculatorTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;

public class JdkDateBackwardDateCalculatorTest extends AbstractBackwardDateCalculatorTest {

    @Override
    protected Object newDate(String date) {
        return Utils.createDate(date);
    }

    @Override
    protected DateCalculatorFactory getDateCalculatorFactory() {
        return DefaultJdkDateCalculatorFactory.getDefaultInstance();
    }

}
