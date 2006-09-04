package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractBackwardDateCalculatorTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkDateBackwardDateCalculatorTest extends AbstractBackwardDateCalculatorTest<Date> {

    @Override
    protected Date newDate(final String date) {
        return Utils.createDate(date);
    }

    @Override
    protected DateCalculatorFactory<Date> getDateCalculatorFactory() {
        return DefaultJdkDateCalculatorFactory.getDefaultInstance();
    }

}
