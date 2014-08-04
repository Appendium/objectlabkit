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

import junit.framework.TestCase;

public abstract class AbstractExcelDateUtilTest<E> extends TestCase {
    protected abstract E createDate(final String str);

    protected abstract E createDateFromExcel(double excelDate, boolean use1904windowing);

    public void testExcelDate() {
        checkDate(createDate("1899-12-31"), 0, false);
        checkDate(createDate("1900-01-01"), 1.0, false);
        checkDate(createDate("1900-03-01"), 61.0, false);
        checkDate(createDate("1968-06-11"), 25000.0, false);
        checkDate(createDate("1978-05-31"), 28641.00, false);
        checkDate(createDate("1999-12-31"), 36525.00, false);
        checkDate(createDate("2000-01-01"), 36526.00, false);
        checkDate(createDate("2000-01-01"), 36526.00, false);
        checkDate(createDate("2000-02-28"), 36584.00, false);
        checkDate(createDate("2000-02-29"), 36585.00, false);
        checkDate(createDate("2000-03-01"), 36586.00, false);
        checkDate(null, -1.0, false);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testExcelDateUsing1904Windowing() {
        checkDate(createDate("1904-01-01"), 0.0, true);
        checkDate(createDate("1904-01-02"), 1.0, true);
        checkDate(createDate("1904-03-02"), 61.0, true);
        checkDate(null, -1.0, false);
    }

    private void checkDate(final E date, final double excelDate, final boolean use1904windowing) {
        assertEquals("excel:" + excelDate, date, createDateFromExcel(excelDate, use1904windowing));
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
