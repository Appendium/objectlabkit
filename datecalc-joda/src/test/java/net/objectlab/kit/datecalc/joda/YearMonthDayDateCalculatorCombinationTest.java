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

import net.objectlab.kit.datecalc.common.AbstractDateCalculatorCombinationTest;
import net.objectlab.kit.datecalc.common.HolidayCalendar;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.WorkingWeek;

import org.joda.time.YearMonthDay;

public class YearMonthDayDateCalculatorCombinationTest extends AbstractDateCalculatorCombinationTest<YearMonthDay> {

    @Override
    protected YearMonthDay newDate(final String date) {
        return new YearMonthDay(date);
    }

    @Override
    protected void registerHolidays(final String name, final HolidayCalendar<YearMonthDay> holidays) {
        YearMonthDayKitCalculatorsFactory.getDefaultInstance().registerHolidays(name, holidays);
    }

    @Override
    protected WorkingWeek getWorkingWeek(final WorkingWeek ww) {
        return new JodaWorkingWeek(ww);
    }

    @Override
    protected KitCalculatorsFactory<YearMonthDay> getDateCalculatorFactory() {
        return YearMonthDayKitCalculatorsFactory.getDefaultInstance();
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
