package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.DateCalculatorFactory;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

public interface JdkDateCalculatorFactory extends DateCalculatorFactory<Date> {

    public abstract JdkDateCalculator getDateCalculator(final String name, final String holidayHandlerType);

    public abstract PeriodCountCalculator<Date> getPeriodCountCalculator();

}