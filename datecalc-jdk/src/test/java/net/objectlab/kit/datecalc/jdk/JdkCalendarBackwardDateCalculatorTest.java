package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractBackwardDateCalculatorTest;
import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkCalendarBackwardDateCalculatorTest extends AbstractBackwardDateCalculatorTest<Calendar> {

    @Override
    protected Calendar newDate(final String date) {
        return Utils.createCalendar(date);
    }

    @Override
    protected DateCalculatorFactory<Calendar> getDateCalculatorFactory() {
        return DefaultJdkCalendarCalculatorFactory.getDefaultInstance();
    }
}
