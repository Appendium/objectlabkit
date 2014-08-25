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
package net.objectlab.kit.datecalc.jdk;

import static net.objectlab.kit.datecalc.common.HolidayHandlerType.BACKWARD;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_FOLLOWING;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_PRECEDING;

import java.util.Calendar;

import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;
import net.objectlab.kit.datecalc.common.SpotLag;

/**
 * The default factory for getting Jdk <code>Calendar</code> based
 * calculators.
 *
 * @author Marcin Jekot
 *
 */
public class CalendarKitCalculatorsFactory extends AbstractKitCalculatorsFactory<Calendar> {

    private static final CalendarKitCalculatorsFactory DEFAULT = new CalendarKitCalculatorsFactory();

    private static final PeriodCountCalculator<Calendar> PCC = new CalendarPeriodCountCalculator();

    private static final CalendarIMMDateCalculator IMMDC = new CalendarIMMDateCalculator();

    public static CalendarKitCalculatorsFactory getDefaultInstance() {
        return DEFAULT;
    }

    /**
     * Return a builder using the registered calendars/working weeks and a Modified Forward Holiday handler for the currency pair; this
     * does NOT copy the calendars or Currency Config.
     *
     * If you want to change some of the parameters, simply modify the Builder returned and pass it to the constructor of the
     * calculator you are interested in.
     */
    public CurrencyDateCalculatorBuilder<Calendar> getDefaultCurrencyDateCalculatorBuilder(final String ccy1, final String ccy2, final SpotLag spotLag) {
        final CurrencyDateCalculatorBuilder<Calendar> builder = new CurrencyDateCalculatorBuilder<Calendar>().currencyPair(ccy1, ccy2, spotLag);

        return configureCurrencyCalculatorBuilder(builder).tenorHolidayHandler(new CalendarModifiedFollowingHandler());
    }

    public static CurrencyDateCalculatorBuilder<Calendar> defaultCurrencyDateCalculatorBuilder(final String ccy1, final String ccy2,
            final SpotLag spotLag) {
        return DEFAULT.getDefaultCurrencyDateCalculatorBuilder(ccy1, ccy2, spotLag);
    }

    public CalendarCurrencyDateCalculator buildCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<Calendar> builder) {
        return new CalendarCurrencyDateCalculator(builder);
    }

    public static CalendarCurrencyDateCalculator forwardCurrencyDateCalculator(final String ccy1, final String ccy2, final SpotLag spotLag) {
        return DEFAULT.getDefaultCurrencyDateCalculator(ccy1, ccy2, spotLag);
    }

    public CalendarCurrencyDateCalculator getDefaultCurrencyDateCalculator(final String ccy1, final String ccy2, final SpotLag spotLag) {
        return new CalendarCurrencyDateCalculator(getDefaultCurrencyDateCalculatorBuilder(ccy1, ccy2, spotLag));
    }

    public static CalendarDateCalculator forwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD);
    }

    public static CalendarDateCalculator backwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.BACKWARD);
    }

    public static CalendarDateCalculator forwardUnlessMovingBackCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK);
    }

    public static CalendarDateCalculator modifiedFollowingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_FOLLOWING);
    }

    public static CalendarDateCalculator modifiedPrecedingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_PRECEDING);
    }

    /**
     * Create a new DateCalculator for a given name and type of handling.
     *
     * @param name
     *            calendar name (holidays set interested in). If there is set of
     *            holidays with that name, it will return a DateCalculator with
     *            an empty holiday set (will work on Weekend only).
     * @param holidayHandlerType
     *            typically one of the value of HolidayHandlerType or null.
     * @return a new DateCalculator
     * @exception IllegalArgumentException
     *                if the type is not null or a valid value.
     */
    public CalendarDateCalculator getDateCalculator(final String name, final String holidayHandlerType) {
        final CalendarDateCalculator cal = new CalendarDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);
        if (holidayHandlerType != null) {
            cal.setHolidayHandler(getHolidayHandler(holidayHandlerType));
        }
        return cal;
    }

    public HolidayHandler<Calendar> getHolidayHandler(final String holidayHandlerType) {
        if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            return new CalendarForwardHandler();
        } else if (BACKWARD.equals(holidayHandlerType)) {
            return new CalendarBackwardHandler();
        } else if (MODIFIED_FOLLOWING.equals(holidayHandlerType)) {
            return new CalendarModifiedFollowingHandler();
        } else if (MODIFIED_PRECEDING.equals(holidayHandlerType)) {
            return new CalendarModifiedPrecedingHandler();
        } else if (FORWARD_UNLESS_MOVING_BACK.equals(holidayHandlerType)) {
            return new CalendarForwardUnlessNegativeHandler();
        } else if (holidayHandlerType != null) {
            throw new IllegalArgumentException("Unsupported HolidayHandler: " + holidayHandlerType);
        }
        return null;
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * Create a new PeriodCountCalculator.
     *
     * @return a PeriodCountCalculator
     */
    public PeriodCountCalculator<Calendar> getPeriodCountCalculator() {
        return PCC;
    }

    /**
     * Create a new IMMDateCalculator.
     *
     * @return an IMMDateCalculator
     */
    public IMMDateCalculator<Calendar> getIMMDateCalculator() {
        return IMMDC;
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
