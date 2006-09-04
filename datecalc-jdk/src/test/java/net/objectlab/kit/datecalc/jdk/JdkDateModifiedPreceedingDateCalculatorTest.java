package net.objectlab.kit.datecalc.jdk;

import net.objectlab.kit.datecalc.common.AbstractModifiedPreceedingDateCalculatorTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateModifiedPreceedingDateCalculatorTest extends AbstractModifiedPreceedingDateCalculatorTest {

    @Override
    protected Object newDate(String date) {
        return Utils.createDate(date);
    }

    @Override
    protected DateCalculatorFactory getDateCalculatorFactory() {
        return DefaultJdkDateCalculatorFactory.getDefaultInstance();
    }

}
