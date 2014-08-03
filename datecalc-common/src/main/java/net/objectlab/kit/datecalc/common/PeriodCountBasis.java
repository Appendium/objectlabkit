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
 * Defines some standard Day Count bases.
 *
 * To read about the most common Day Count Conventions, you can refer to the
 * <a href="http://www.fincad.com/support/developerfunc/mathref/Daycount.htm">
 * following document</a> or <a href="https://en.wikipedia.org/wiki/Day_count_convention">here</a>.
 *
 * @author Benoit Xhenseval
 */
public enum PeriodCountBasis {
    /**
     * 30/360, means that day count fractions are calculated assuming 30 day
     * months and 360 days in a year. The second date is only adjusted if the
     * first date is on the 31st of a month.
     */
    CONV_30_360,

    /**
     * 30E/360, means that day count fractions are calculated assuming 30 day
     * months and 360 days in a year. In this case, the second date is always
     * adjusted to the 30th, if it is the 31st.
     */
    CONV_360E_ISDA,

    /**
     * 30E+/360, means that day count fractions are calculated assuming 30 day
     * months and 360 days in a year. If the second date is on the 31st, it is
     * adjusted to the 30th and a second month is incremented by one.
     */
    CONV_360E_ISMA,

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * Actual/Actual, means that the numerator is the number of days between the
     * two dates. The denominator is the actual number of days in the coupon
     * period multiplied by the coupon frequency. This is mainly relates to
     * bonds. This normally results in day count factors of 1.0 for annual
     * coupons, 0.5 for semi annual coupons and 0.25 for quarterly coupons.
     */
    ACT_ACT,

    /**
     * Actual/360, known in the United States as money market basis, has a day
     * count fraction equal to the number of days between the payment dates,
     * divided by 360.
     */
    ACT_360,

    /**
     * Actual/365(Fixed), known as bond basis in the United States, means that
     * the day count fraction is equal to the number of days between the last
     * payment date and the next date divided by 365.
     */
    ACT_365
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
