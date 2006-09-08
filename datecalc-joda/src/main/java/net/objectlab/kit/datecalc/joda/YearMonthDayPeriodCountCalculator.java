package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.PeriodCountBasis;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

import org.joda.time.YearMonthDay;

public class YearMonthDayPeriodCountCalculator implements PeriodCountCalculator<YearMonthDay> {

    private static final LocalDatePeriodCountCalculator DELEGATE = new LocalDatePeriodCountCalculator();

    public int dayDiff(final YearMonthDay start, final YearMonthDay end, final PeriodCountBasis basis) {
        return DELEGATE.dayDiff(start.toLocalDate(), end.toLocalDate(), basis);
    }

    public double monthDiff(final YearMonthDay start, final YearMonthDay end, final PeriodCountBasis basis) {
        return DELEGATE.monthDiff(start.toLocalDate(), end.toLocalDate(), basis);
    }

    public double yearDiff(final YearMonthDay start, final YearMonthDay end, final PeriodCountBasis basis) {
        return DELEGATE.yearDiff(start.toLocalDate(), end.toLocalDate(), basis);
    }
}
