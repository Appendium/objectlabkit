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

import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandler;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

/**
 * A Wrapper to handle any HolidayHandler&lt;LocalDate&gt; types via a HolidayHandler&lt;YearMonthDay&gt;
 * delegate
 *
 * @author Benoit Xhenseval
 *
 */
public class HolidayHandlerYearMonthDayWrapper implements HolidayHandler<LocalDate> {

    private final HolidayHandler<YearMonthDay> delegate;

    private final DateCalculator<YearMonthDay> calculator;

    /**
     * If the current date of the give calculator is a non-working day, it will
     * be moved according to the algorithm implemented.
     *
     * @param calculator
     *            the calculator
     */
    public HolidayHandlerYearMonthDayWrapper(final HolidayHandler<YearMonthDay> delegate, final DateCalculator<YearMonthDay> calculator) {
        this.delegate = delegate;
        this.calculator = calculator;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayHandler#getType()
     */
    public String getType() {
        return delegate.getType();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.common.HolidayHandler#moveCurrentDate(net.objectlab.kit.datecalc.common.DateCalculator)
     */
    public LocalDate moveCurrentDate(final DateCalculator<LocalDate> calc) {
        LocalDate ret = calc.getCurrentBusinessDate();
        if (delegate != null) {
            final YearMonthDay day = delegate.moveCurrentDate(calculator);
            if (day != null) {
                ret = day.toLocalDate();
            }
        }
        return ret;
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
