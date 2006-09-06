package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

public interface JdkCalendarCalculatorFactory extends DateCalculatorFactory<Calendar> {

    JdkCalendarDateCalculator getDateCalculator(final String name, final String holidayHandlerType);

    PeriodCountCalculator<Calendar> getPeriodCountCalculator();

}