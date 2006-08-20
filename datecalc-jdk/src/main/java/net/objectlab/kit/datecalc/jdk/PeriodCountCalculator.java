package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.PeriodCountBasis;

public interface PeriodCountCalculator {
    int dayDiff(final Date start, final Date end, PeriodCountBasis basis);

    double monthDiff(final Date start, final Date end, PeriodCountBasis basis);

    double yearDiff(final Date start, final Date end, PeriodCountBasis basis);
}
