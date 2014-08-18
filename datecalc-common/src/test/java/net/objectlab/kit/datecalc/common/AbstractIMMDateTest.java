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

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public abstract class AbstractIMMDateTest<E> extends TestCase {

    private IMMDateCalculator<E> cal;

    @Override
    public void setUp() {
        cal = getDateCalculator("bla");
    }

    protected abstract IMMDateCalculator<E> getDateCalculator(String name);

    protected abstract E parseDate(String string);

    private void checkImm(final IMMDateCalculator<E> cal, final E date, final boolean expected) {
        assertEquals("check " + date, expected, cal.isIMMDate(date));
    }

    public void testNextIMM() {
        E startDate = parseDate("2006-08-01");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-01-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-02-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-03-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-04-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-05-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-06-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-07-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-08-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-09-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-10-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-12-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-11-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-12-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-12-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-12-20"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-03-14");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-03-15");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-03-16");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-06-20");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-06-21");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-06-22");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-09-19");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-09-20");
        Assert.assertEquals("From " + startDate, parseDate("2006-12-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-09-21");
        Assert.assertEquals("From " + startDate, parseDate("2006-12-20"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-12-19");
        Assert.assertEquals("From " + startDate, parseDate("2006-12-20"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-12-20");
        Assert.assertEquals("From " + startDate, parseDate("2007-03-21"), cal.getNextIMMDate(startDate));
        startDate = parseDate("2006-12-21");
        Assert.assertEquals("From " + startDate, parseDate("2007-03-21"), cal.getNextIMMDate(startDate));

        startDate = parseDate("2006-03-15");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getNextIMMDate(startDate));
    }

    public void testNextIMMWithPeriod() {
        E startDate = parseDate("2006-08-01");

        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getNextIMMDate(startDate));

        IMMPeriod period = IMMPeriod.QUARTERLY;

        startDate = parseDate("2006-01-09");
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-03-15"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.BI_ANNUALY_JUN_DEC;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-06-21"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.BI_ANNUALY_MAR_SEP;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-03-15"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.ANNUALLY;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2007-03-21"), cal.getNextIMMDate(startDate, period));

        startDate = parseDate("2006-03-20");
        period = IMMPeriod.QUARTERLY;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-06-21"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.BI_ANNUALY_JUN_DEC;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-06-21"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.BI_ANNUALY_MAR_SEP;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-09-20"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.ANNUALLY;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2007-06-20"), cal.getNextIMMDate(startDate, period));

        startDate = parseDate("2006-03-15");
        period = IMMPeriod.QUARTERLY;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-06-21"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.BI_ANNUALY_JUN_DEC;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-06-21"), cal.getNextIMMDate(startDate, period));
        period = IMMPeriod.BI_ANNUALY_MAR_SEP;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2006-09-20"), cal.getNextIMMDate(startDate, period));
        startDate = parseDate("2006-03-15");
        period = IMMPeriod.ANNUALLY;
        Assert.assertEquals("From " + startDate + " period:" + period, parseDate("2007-06-20"), cal.getNextIMMDate(startDate, period));
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public void testIfIMMDate() {
        checkImm(cal, parseDate("2006-08-01"), false);

        checkImm(cal, parseDate("2006-03-14"), false);
        checkImm(cal, parseDate("2006-03-15"), true);
        checkImm(cal, parseDate("2006-03-16"), false);

        checkImm(cal, parseDate("2006-06-20"), false);
        checkImm(cal, parseDate("2006-06-21"), true);
        checkImm(cal, parseDate("2006-06-22"), false);

        checkImm(cal, parseDate("2006-09-19"), false);
        checkImm(cal, parseDate("2006-09-20"), true);
        checkImm(cal, parseDate("2006-09-21"), false);

        checkImm(cal, parseDate("2006-12-19"), false);
        checkImm(cal, parseDate("2006-12-20"), true);
        checkImm(cal, parseDate("2006-12-21"), false);
    }

    public void testPreviousIMM() {
        E startDate = parseDate("2006-08-01");

        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getPreviousIMMDate(startDate));

        startDate = parseDate("2006-01-09");
        Assert.assertEquals("From " + startDate, parseDate("2005-12-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-02-09");
        Assert.assertEquals("From " + startDate, parseDate("2005-12-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-03-09");
        Assert.assertEquals("From " + startDate, parseDate("2005-12-21"), cal.getPreviousIMMDate(startDate));

        startDate = parseDate("2006-04-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-05-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-06-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getPreviousIMMDate(startDate));

        startDate = parseDate("2006-07-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-08-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-09-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getPreviousIMMDate(startDate));

        startDate = parseDate("2006-10-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-11-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-12-09");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getPreviousIMMDate(startDate));

        // close to dates

        startDate = parseDate("2006-03-14");
        Assert.assertEquals("From " + startDate, parseDate("2005-12-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-03-15");
        Assert.assertEquals("From " + startDate, parseDate("2005-12-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-03-16");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getPreviousIMMDate(startDate));

        startDate = parseDate("2006-06-20");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-06-21");
        Assert.assertEquals("From " + startDate, parseDate("2006-03-15"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-06-22");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getPreviousIMMDate(startDate));

        startDate = parseDate("2006-09-19");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-09-20");
        Assert.assertEquals("From " + startDate, parseDate("2006-06-21"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-09-21");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getPreviousIMMDate(startDate));

        startDate = parseDate("2006-12-19");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-12-20");
        Assert.assertEquals("From " + startDate, parseDate("2006-09-20"), cal.getPreviousIMMDate(startDate));
        startDate = parseDate("2006-12-21");
        Assert.assertEquals("From " + startDate, parseDate("2006-12-20"), cal.getPreviousIMMDate(startDate));
    }

    public void testIMMLists() {
        final E startDate = parseDate("2005-12-01");
        final E endDate = parseDate("2007-03-17");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("Not empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 5, imms.size());
        Assert.assertEquals("date 1", parseDate("2005-12-21"), imms.get(0));
        Assert.assertEquals("date 2", parseDate("2006-03-15"), imms.get(1));
        Assert.assertEquals("date 3", parseDate("2006-06-21"), imms.get(2));
        Assert.assertEquals("date 4", parseDate("2006-09-20"), imms.get(3));
        Assert.assertEquals("date 5", parseDate("2006-12-20"), imms.get(4));
    }

    public void testEmptyIMMLists() {
        final E startDate = parseDate("2006-03-16");
        final E endDate = parseDate("2006-06-20");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", imms.isEmpty());
    }

    public void testNext0IMMList() {
        final E startDate = parseDate("2006-03-16");
        final List<E> imms = cal.getNextIMMDates(startDate, 0);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", imms.isEmpty());
    }

    public void testNext1IMMList() {
        final E startDate = parseDate("2006-03-16");
        final List<E> imms = cal.getNextIMMDates(startDate, 1);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 1, imms.size());
        Assert.assertEquals("date 1", parseDate("2006-06-21"), imms.get(0));
    }

    public void testNext5IMMList() {
        final E startDate = parseDate("2006-03-16");
        final List<E> imms = cal.getNextIMMDates(startDate, 5);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 5, imms.size());
        Assert.assertEquals("date 1", parseDate("2006-06-21"), imms.get(0));
        Assert.assertEquals("date 2", parseDate("2006-09-20"), imms.get(1));
        Assert.assertEquals("date 3", parseDate("2006-12-20"), imms.get(2));
        Assert.assertEquals("date 4", parseDate("2007-03-21"), imms.get(3));
        Assert.assertEquals("date 5", parseDate("2007-06-20"), imms.get(4));
    }

    public void testEndOnIMMDateIMMLists() {
        final E startDate = parseDate("2006-03-16");
        final E endDate = parseDate("2006-06-21");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", !imms.isEmpty());
        Assert.assertEquals("Expected number of imms dates", 1, imms.size());
        Assert.assertEquals("date 1", parseDate("2006-06-21"), imms.get(0));
    }

    public void testStartOnIMMDateIMMLists() {
        final E startDate = parseDate("2006-03-15");
        final E endDate = parseDate("2006-06-20");
        final List<E> imms = cal.getIMMDates(startDate, endDate);
        Assert.assertNotNull(imms);
        Assert.assertTrue("empty", imms.isEmpty());
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
