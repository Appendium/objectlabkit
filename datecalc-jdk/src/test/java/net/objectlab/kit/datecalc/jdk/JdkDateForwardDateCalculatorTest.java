package net.objectlab.kit.datecalc.jdk;

import net.objectlab.kit.datecalc.common.AbstractForwardDateCalculatorTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateForwardDateCalculatorTest extends AbstractForwardDateCalculatorTest {

    @Override
    protected Object newDate(String date) {
        return Utils.createDate(date);
    }

    @Override
    protected DateCalculatorFactory getDateCalculatorFactory() {
        return DefaultJdkDateCalculatorFactory.getDefaultInstance();
    }

}
