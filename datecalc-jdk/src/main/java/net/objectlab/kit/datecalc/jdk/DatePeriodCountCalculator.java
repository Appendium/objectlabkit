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
package net.objectlab.kit.datecalc.jdk;

import java.util.Date;

import net.objectlab.kit.datecalc.common.PeriodCountBasis;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;
import net.objectlab.kit.datecalc.common.Utils;

/**
 * Jdk <code>Date</code> based implementation of the {@link PeriodCountCalculator}
 * 
 * @author Marcin Jekot
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class DatePeriodCountCalculator implements PeriodCountCalculator<Date> {

    private static final CalendarPeriodCountCalculator PCC = new CalendarPeriodCountCalculator();

    public int dayDiff(final Date start, final Date end, final PeriodCountBasis basis) {

        return PCC.dayDiff(Utils.getCal(start), Utils.getCal(end), basis);
    }

    public double monthDiff(final Date start, final Date end, final PeriodCountBasis basis) {
        return PCC.monthDiff(Utils.getCal(start), Utils.getCal(end), basis);
    }

    public double yearDiff(final Date start, final Date end, final PeriodCountBasis basis) {
        return PCC.yearDiff(Utils.getCal(start), Utils.getCal(end), basis);
    }
}
