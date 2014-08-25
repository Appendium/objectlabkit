package net.objectlab.kit.datecalc.jdk;

import java.util.Calendar;
import java.util.Date;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.ccy.AbstractCurrencyDateCalculator;

/**
 * JDK Date implementation for currency date calculator.
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public class DateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<Date> {

    public DateCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<Date> builder) {
        super(builder);
    }

    @Override
    protected Date addMonths(final Date calc, final int unit) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(calc);
        cal.add(Calendar.MONTH, unit);
        return cal.getTime();
    }

    @Override
    protected Date calculateNextDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    @Override
    protected int calendarWeekDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    @Override
    protected Date max(final Date d1, final Date d2) {
        return d1.compareTo(d2) > 0 ? d1 : d2;
    }

}
