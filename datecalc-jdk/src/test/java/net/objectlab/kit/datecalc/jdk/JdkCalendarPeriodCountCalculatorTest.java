package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractPeriodCountCalculatorTest;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;
import net.objectlab.kit.datecalc.common.Utils;

public class JdkCalendarPeriodCountCalculatorTest extends AbstractPeriodCountCalculatorTest<Calendar> {

    @Override
    public PeriodCountCalculator<Calendar> getPeriodCountCalculator() {
        return CalendarKitCalculatorsFactory.getDefaultInstance().getPeriodCountCalculator();
    }

    @Override
    public Calendar parseDate(final String string) {
        return Utils.createCalendar(string);
    }

    @Override
    public Calendar getDate() {
        return Calendar.getInstance();
    }

}