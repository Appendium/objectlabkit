package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.ccy.AbstractCurrencyDateCalculator;

import org.joda.time.LocalDate;

/**
 * Joda LocalDate implementation for currency date calculator.
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public class LocalDateCurrencyDateCalculator extends AbstractCurrencyDateCalculator<LocalDate> {

    public LocalDateCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<LocalDate> builder) {
        super(builder);
    }

    @Override
    protected LocalDate addMonths(LocalDate date, int unit) {
        return date.plusMonths(unit);
    }

    @Override
    protected LocalDate calculateNextDay(final LocalDate date) {
        return date.plusDays(1);
    }

    @Override
    protected int calendarWeekDay(final LocalDate date) {
        return JodaWorkingWeek.jodaToCalendarDayConstant(date);
    }

    @Override
    protected LocalDate max(final LocalDate d1, final LocalDate d2) {
        return d1.isAfter(d2) ? d1 : d2;
    }

}
