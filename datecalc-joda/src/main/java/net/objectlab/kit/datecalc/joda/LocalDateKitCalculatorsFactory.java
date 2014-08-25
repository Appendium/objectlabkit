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

import static net.objectlab.kit.datecalc.common.HolidayHandlerType.BACKWARD;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_FOLLOWING;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_PRECEDING;
import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;
import net.objectlab.kit.datecalc.common.SpotLag;

import org.joda.time.LocalDate;

/**
 * The default factory for getting Joda <code>LocalDate</code> based
 * calculators.
 *
 * @author Benoit Xhenseval
 *
 */
public class LocalDateKitCalculatorsFactory extends AbstractKitCalculatorsFactory<LocalDate> {

    private static final LocalDateKitCalculatorsFactory DEFAULT = new LocalDateKitCalculatorsFactory();

    private static final PeriodCountCalculator<LocalDate> PCC = new LocalDatePeriodCountCalculator();

    private static final IMMDateCalculator<LocalDate> IMMDC = new LocalDateIMMDateCalculator();

    public static LocalDateKitCalculatorsFactory getDefaultInstance() {
        return DEFAULT;
    }

    /**
     * Return a builder using the registered calendars/working weeks and a Modified Forward Holiday handler for the currency pair; .
     *
     * If you want to change some of the parameters, simply modify the Builder returned and pass it to the constructor of the
     * calculator you are interested in.
     */
    public CurrencyDateCalculatorBuilder<LocalDate> getDefaultCurrencyDateCalculatorBuilder(final String ccy1, final String ccy2,
            final SpotLag spotLag) {
        final CurrencyDateCalculatorBuilder<LocalDate> builder = new CurrencyDateCalculatorBuilder<LocalDate>().currencyPair(ccy1, ccy2, spotLag);

        return configureCurrencyCalculatorBuilder(builder).tenorHolidayHandler(new LocalDateModifiedFollowingHandler());
    }

    public static CurrencyDateCalculatorBuilder<LocalDate> defaultCurrencyDateCalculatorBuilder(final String ccy1, final String ccy2,
            final SpotLag spotLag) {
        return DEFAULT.getDefaultCurrencyDateCalculatorBuilder(ccy1, ccy2, spotLag);
    }

    public static LocalDateCurrencyDateCalculator forwardCurrencyDateCalculator(final String ccy1, final String ccy2, final SpotLag spotLag) {
        return DEFAULT.getDefaultCurrencyDateCalculator(ccy1, ccy2, spotLag);
    }

    public LocalDateCurrencyDateCalculator getDefaultCurrencyDateCalculator(final String ccy1, final String ccy2, final SpotLag spotLag) {
        return new LocalDateCurrencyDateCalculator(getDefaultCurrencyDateCalculatorBuilder(ccy1, ccy2, spotLag));
    }

    public LocalDateCurrencyDateCalculator buildCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<LocalDate> builder) {
        return new LocalDateCurrencyDateCalculator(builder);
    }

    public static LocalDateCalculator forwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD);
    }

    public static LocalDateCalculator backwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.BACKWARD);
    }

    public static LocalDateCalculator forwardUnlessMovingBackCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK);
    }

    public static LocalDateCalculator modifiedFollowingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_FOLLOWING);
    }

    public static LocalDateCalculator modifiedPrecedingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_PRECEDING);
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * Create a new DateCalculator for a given name and type of handling.
     *
     * @param name
     *            calendar name (holidays set interested in). If there is set of
     *            holidays with that name, it will return a DateCalculator with
     *            an empty holiday set (will work on Weekend only).
     * @param holidayHandlerType
     *            typically one of the value of HolidayHandlerType
     * @return a new DateCalculator
     */
    public LocalDateCalculator getDateCalculator(final String name, final String holidayHandlerType) {
        final LocalDateCalculator cal = new LocalDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        cal.setHolidayHandler(getHolidayHandler(holidayHandlerType));
        return cal;
    }

    public HolidayHandler<LocalDate> getHolidayHandler(final String holidayHandlerType) {
        if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            return new LocalDateForwardHandler();
        } else if (BACKWARD.equals(holidayHandlerType)) {
            return new LocalDateBackwardHandler();
        } else if (MODIFIED_FOLLOWING.equals(holidayHandlerType)) {
            return new LocalDateModifiedFollowingHandler();
        } else if (MODIFIED_PRECEDING.equals(holidayHandlerType)) {
            return new LocalDateModifiedPrecedingHandler();
        } else if (FORWARD_UNLESS_MOVING_BACK.equals(holidayHandlerType)) {
            return new LocalDateForwardUnlessNegativeHandler();
        } else if (holidayHandlerType != null) {
            throw new IllegalArgumentException("Unsupported HolidayHandler: " + holidayHandlerType);
        }
        return null;
    }

    public PeriodCountCalculator<LocalDate> getPeriodCountCalculator() {
        return PCC;
    }

    public IMMDateCalculator<LocalDate> getIMMDateCalculator() {
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
