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
package net.objectlab.kit.datecalc.common;

/**
 * Interface that defines a financial market way of calculating difference in
 * days, month (or part of) and year (or part of).
 *
 * @author Benoit Xhenseval
 *
 * @param <E>
 *            a representation of a date, typically JDK: Date, Calendar;
 *            Joda:LocalDate, YearMonthDay
 *
 */
public interface PeriodCountCalculator<E> {
    /**
     * This calculates the number of days between 2 dates, it follows the given
     * basis which means that the result could vary between the same 2 dates if
     * the basis is different.
     *
     * @param start
     *            the start date
     * @param end
     *            the end date
     * @param basis
     *            the basis to use
     * @return number of days between end and start.
     */
    int dayDiff(final E start, final E end, PeriodCountBasis basis);

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * This calculates the number of months (or fraction) between 2 dates, it
     * follows the given basis which means that the result could vary between
     * the same 2 dates if the basis is different.
     *
     * @param start
     *            the start date
     * @param end
     *            the end date
     * @param basis
     *            the basis to use
     * @return number of months between end and start.
     */
    double monthDiff(final E start, final E end, PeriodCountBasis basis);

    /**
     * This calculates the number of years (or fraction) between 2 dates, it
     * follows the given basis which means that the result could vary between
     * the same 2 dates if the basis is different.
     *
     * @param start
     *            the start date
     * @param end
     *            the end date
     * @param basis
     *            the basis to use
     * @return number of months between end and start.
     */
    double yearDiff(final E start, final E end, PeriodCountBasis basis);
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
