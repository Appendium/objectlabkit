/*
 * $Id: org.eclipse.jdt.ui.prefs 59 2006-08-26 09:06:39Z marchy $
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

import java.util.HashSet;
import java.util.Set;

import net.objectlab.kit.datecalc.common.DateCalculator;

import org.joda.time.YearMonthDay;

/**
 * 
 * @author xhensevb
 * @author $LastChangedBy: marchy $
 * @version $Revision: 59 $ $Date: 2006-08-26 11:06:39 +0200 (Sat, 26 Aug 2006) $
 * 
 */
public class YearMonthDayModifiedPrecedingDateCalculatorTest extends AbstractModifiedFollowingDateCalculatorTest<YearMonthDay> {

    @Override
    protected YearMonthDay newDate(final String date) {
        return new YearMonthDay(date);
    }

    @Override
    protected DateCalculator<YearMonthDay> newDateCalculator(final String name, final String type) {
        return DefaultYearMonthDayCalculatorFactory.getDefaultInstance().getDateCalculator(name, type);
    }

    @Override
    protected Set<YearMonthDay> newHolidaysSet() {
        final Set<YearMonthDay> holidays = new HashSet<YearMonthDay>();
        holidays.add(newDate("2006-08-28"));
        holidays.add(newDate("2006-12-25"));
        holidays.add(newDate("2006-12-26"));
        return holidays;
    }
}
