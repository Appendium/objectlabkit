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

import org.joda.time.YearMonthDay;

/**
 * Joda <code>YearMonthDay</code> based implementation of the {@link PeriodCountCalculator}.
 * It just delegates to a <code>PeriodCountCalculator<LocalDate></code>
 *
 * @author Benoit Xhenseval
 * @author $LastChangedBy: marchy $
 * @version $Revision: 138 $ $Date: 2006-09-10 14:29:15 +0200 (Sun, 10 Sep 2006) $
 *
 */
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
