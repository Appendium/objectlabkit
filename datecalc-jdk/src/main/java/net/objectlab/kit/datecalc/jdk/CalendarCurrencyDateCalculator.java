package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.ccy.AbstractCurrencyDateCalculator;

/**
 * JDK Calendar implementation for currency date calculator.
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public class CalendarCurrencyDateCalculator extends AbstractCurrencyDateCalculator<Calendar> {

    public CalendarCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<Calendar> builder) {
        super(builder);
    }

    @Override
    protected Calendar addMonths(final Calendar calc, final int unit) {
        calc.add(Calendar.MONTH, unit);
        return calc;
    }

    @Override
    protected Calendar calculateNextDay(final Calendar date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date.getTime());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal;
    }

    @Override
    protected int calendarWeekDay(final Calendar date) {
        return date.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    protected Calendar max(final Calendar d1, final Calendar d2) {
        return d1.compareTo(d2) > 0 ? d1 : d2;
    }

}
