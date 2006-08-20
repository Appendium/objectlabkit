package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.PeriodCountBasis;

import org.joda.time.LocalDate;

public interface PeriodCountCalculator {
    int dayDiff(final LocalDate start, final LocalDate end, PeriodCountBasis basis);

    double monthDiff(final LocalDate start, final LocalDate end, PeriodCountBasis basis);

    double yearDiff(final LocalDate start, final LocalDate end, PeriodCountBasis basis);
}
