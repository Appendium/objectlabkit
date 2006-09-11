/*
 * $Id$
 * 
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package net.objectlab.kit.datecalc.joda;

import net.objectlab.kit.datecalc.common.PeriodCountBasis;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

/**
 * Joda <code>LocalDatePeriod</code> based implementation of the
 * {@link PeriodCountCalculator}
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy: marchy $
 * @version $Revision: 138 $ $Date: 2006-09-10 14:29:15 +0200 (Sun, 10 Sep 2006) $
 * 
 */
public class LocalDatePeriodCountCalculator implements PeriodCountCalculator<LocalDate> {

    public int dayDiff(final LocalDate start, final LocalDate end, final PeriodCountBasis basis) {
        int diff = 0;

        if (basis == PeriodCountBasis.CONV_30_360) {
            int dayStart = start.getDayOfMonth();
            int dayEnd = end.getDayOfMonth();
            if (dayEnd == MONTH_31_DAYS && dayStart >= MONTH_30_DAYS) {
                dayEnd = MONTH_30_DAYS;
            }
            if (dayStart == MONTH_31_DAYS) {
                dayStart = MONTH_30_DAYS;
            }
            diff = (end.getYear() - start.getYear()) * YEAR_360 + (end.getMonthOfYear() - start.getMonthOfYear()) * MONTH_30_DAYS
                    + dayEnd - dayStart;

        } else if (basis == PeriodCountBasis.CONV_360E_ISDA) {
            int dayStart = start.getDayOfMonth();
            int dayEnd = end.getDayOfMonth();
            final int monthStart = start.getMonthOfYear();
            if ((monthStart == 2 && start.monthOfYear().getMaximumValue() == dayStart) || dayEnd == MONTH_31_DAYS) {
                dayEnd = MONTH_30_DAYS;
            }
            if (dayStart == MONTH_31_DAYS) {
                dayStart = MONTH_30_DAYS;
            }

            diff = (end.getYear() - start.getYear()) * YEAR_360 + (end.getMonthOfYear() - start.getMonthOfYear()) * MONTH_30_DAYS
                    + dayEnd - dayStart;

        } else if (basis == PeriodCountBasis.CONV_360E_ISMA) {
            int dayStart = start.getDayOfMonth();
            int dayEnd = end.getDayOfMonth();
            if (dayEnd == MONTH_31_DAYS) {
                dayEnd = MONTH_30_DAYS;
            }
            if (dayStart == MONTH_31_DAYS) {
                dayStart = MONTH_30_DAYS;
            }
            diff = (end.getYear() - start.getYear()) * YEAR_360 + (end.getMonthOfYear() - start.getMonthOfYear()) * MONTH_30_DAYS
                    + dayEnd - dayStart;
        } else {

            final Period p = new Period(start, end, PeriodType.days());
            diff = p.getDays();
        }
        return diff;
    }

    public double monthDiff(final LocalDate start, final LocalDate end, final PeriodCountBasis basis) {
        return yearDiff(start, end, basis) * MONTHS_IN_YEAR;
    }

    public double yearDiff(final LocalDate start, final LocalDate end, final PeriodCountBasis basis) {
        double diff = 0.0;

        if (basis == PeriodCountBasis.ACT_ACT) {
            final int startYear = start.getYear();
            final int endYear = end.getYear();
            if (startYear != endYear) {
                final LocalDate endOfStartYear = start.dayOfYear().withMaximumValue();
                final LocalDate startOfEndYear = end.withDayOfYear(1);

                final int diff1 = new Period(start, endOfStartYear, PeriodType.days()).getDays();
                final int diff2 = new Period(startOfEndYear, end, PeriodType.days()).getDays();
                diff = ((diff1 + 1.0)) / start.dayOfYear().getMaximumValue()
                        + ((endYear - startYear - 1.0)) + ((double) (diff2)) / (double) end.dayOfYear().getMaximumValue();
            }

        } else if (basis == PeriodCountBasis.CONV_30_360 || basis == PeriodCountBasis.CONV_360E_ISDA
                || basis == PeriodCountBasis.CONV_360E_ISMA || basis == PeriodCountBasis.ACT_360) {
            diff = (dayDiff(start, end, basis)) / YEAR_360_0;

        } else if (basis == PeriodCountBasis.ACT_365 || basis == PeriodCountBasis.END_365) {
            diff = (dayDiff(start, end, basis)) / YEAR_365_0;
        } else {
            throw new UnsupportedOperationException("Sorry no ACT_UST yet");
        }
        return diff;
    }
}
