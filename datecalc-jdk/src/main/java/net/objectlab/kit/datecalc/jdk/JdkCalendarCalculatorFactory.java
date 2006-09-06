package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

public interface JdkCalendarCalculatorFactory extends DateCalculatorFactory<Calendar> {

    public abstract JdkCalendarDateCalculator getDateCalculator(final String name, final String holidayHandlerType);

    public abstract PeriodCountCalculator<Calendar> getPeriodCountCalculator();

}