package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractIMMDateTest;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkCalendarIMMDateTest extends AbstractIMMDateTest<Calendar> {

    @Override
    protected IMMDateCalculator<Calendar> getDateCalculator(final String name) {
        return DefaultJdkCalendarCalculatorFactory.getDefaultInstance().getIMMDateCalculator();
    }

    @Override
    protected Calendar parseDate(final String string) {
        return Utils.createCalendar(string);
    }

}