/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 *
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
 * Joda <code>YearMonthDay</code> based implementation of the
 * {@link net.objectlab.kit.datecalc.common.PeriodCountCalculator}. It just
 * delegates to a <code>PeriodCountCalculator&lt;LocalDate&gt;</code>
 * 
 * @author Benoit Xhenseval
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

/*
 * ObjectLab, http://www.objectlab.co.uk/open is sponsoring the ObjectLab Kit.
 * 
 * Based in London, we are world leaders in the design and development 
 * of bespoke applications for the securities financing markets.
 * 
 * <a href="http://www.objectlab.co.uk/open">Click here to learn more about us</a>
 *           ___  _     _           _   _          _
 *          / _ \| |__ (_) ___  ___| |_| |    __ _| |__
 *         | | | | '_ \| |/ _ \/ __| __| |   / _` | '_ \
 *         | |_| | |_) | |  __/ (__| |_| |__| (_| | |_) |
 *          \___/|_.__// |\___|\___|\__|_____\__,_|_.__/
 *                   |__/
 *
 *                     www.ObjectLab.co.uk
 */
