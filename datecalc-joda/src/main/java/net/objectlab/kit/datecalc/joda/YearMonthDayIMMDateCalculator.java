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

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.datecalc.common.AbstractIMMDateCalculator;
import net.objectlab.kit.datecalc.common.IMMPeriod;

import org.joda.time.LocalDate;
import org.joda.time.YearMonthDay;

/**
 * Joda <code>YearMonthDay</code> based implementation of the
 * {@link IMMDateCalculator}
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy: marchy $
 * @version $Revision: 138 $ $Date: 2006-09-10 14:29:15 +0200 (Sun, 10 Sep 2006) $
 * 
 */
public class YearMonthDayIMMDateCalculator extends AbstractIMMDateCalculator<YearMonthDay> {

    private static final LocalDateIMMDateCalculator DELEGATE = new LocalDateIMMDateCalculator();

    public boolean isIMMDate(final YearMonthDay date) {
        return DELEGATE.isIMMDate(date.toLocalDate());
    }

    @Override
    protected YearMonthDay getNextIMMDate(final boolean requestNextIMM, final YearMonthDay theStartDate, final IMMPeriod period) {
        return new YearMonthDay(DELEGATE.getNextIMMDate(requestNextIMM, theStartDate.toLocalDate(), period));
    }

    public List<YearMonthDay> getIMMDates(final YearMonthDay start, final YearMonthDay end, final IMMPeriod period) {
        return buildList(DELEGATE.getIMMDates(start.toLocalDate(), end.toLocalDate(), period));
    }

    private List<YearMonthDay> buildList(final List<LocalDate> dates) {
        final List<YearMonthDay> imms = new ArrayList<YearMonthDay>();
        for (final LocalDate date : dates) {
            imms.add(new YearMonthDay(date));
        }
        return imms;
    }
}
