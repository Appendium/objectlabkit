package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorCombinationTest;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkCalendarDateCalculatorCombinationTest extends AbstractDateCalculatorCombinationTest<Calendar> {

    @Override
    protected Calendar newDate(final String date) {
        return Utils.createCalendar(date);
    }

    @Override
    protected KitCalculatorsFactory<Calendar> getDateCalculatorFactory() {
        return CalendarKitCalculatorsFactory.getDefaultInstance();
    }

}
