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

import java.util.Date;

import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.CurrencyDateCalculatorBuilder;
import net.objectlab.kit.datecalc.common.HolidayHandler;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;
import net.objectlab.kit.datecalc.common.SpotLag;

/**
 * The default factory for getting Jdk <code>Date</code> based calculators.
 *
 * @author Benoit Xhenseval
 *
 */
public class DateKitCalculatorsFactory extends AbstractKitCalculatorsFactory<Date> {

    private static final DateKitCalculatorsFactory DEFAULT = new DateKitCalculatorsFactory();

    private static final PeriodCountCalculator<Date> PCC = new DatePeriodCountCalculator();

    private static final DateIMMDateCalculator IMMDC = new DateIMMDateCalculator();

    public static DateKitCalculatorsFactory getDefaultInstance() {
        return DEFAULT;
    }

    /**
     * Return a builder using the registered calendars/working weeks and a Modified Forward Holiday handler for the currency pair; this
     * does NOT copy the calendars or Currency Config.
     *
     * If you want to change some of the parameters, simply modify the Builder returned and pass it to the constructor of the
     * calculator you are interested in.
     */
    public CurrencyDateCalculatorBuilder<Date> getDefaultCurrencyDateCalculatorBuilder(final String ccy1, final String ccy2, final SpotLag spotLag) {
        final CurrencyDateCalculatorBuilder<Date> builder = new CurrencyDateCalculatorBuilder<Date>().currencyPair(ccy1, ccy2, spotLag);

        return configureCurrencyCalculatorBuilder(builder).tenorHolidayHandler(new DateModifiedFollowingHandler());
    }

    public static CurrencyDateCalculatorBuilder<Date> defaultCurrencyDateCalculatorBuilder(final String ccy1, final String ccy2, final SpotLag spotLag) {
        return DEFAULT.getDefaultCurrencyDateCalculatorBuilder(ccy1, ccy2, spotLag);
    }

    public DateCurrencyDateCalculator buildCurrencyDateCalculator(final CurrencyDateCalculatorBuilder<Date> builder) {
        return new DateCurrencyDateCalculator(builder);
    }

    public static DateCurrencyDateCalculator forwardCurrencyDateCalculator(final String ccy1, final String ccy2, final SpotLag spotLag) {
        return DEFAULT.getDefaultCurrencyDateCalculator(ccy1, ccy2, spotLag);
    }

    public DateCurrencyDateCalculator getDefaultCurrencyDateCalculator(final String ccy1, final String ccy2, final SpotLag spotLag) {
        return new DateCurrencyDateCalculator(DEFAULT.getDefaultCurrencyDateCalculatorBuilder(ccy1, ccy2, spotLag));
    }

    public static DateDateCalculator forwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD);
    }

    public static DateDateCalculator backwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.BACKWARD);
    }

    public static DateDateCalculator forwardUnlessMovingBackCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK);
    }

    public static DateDateCalculator modifiedFollowingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_FOLLOWING);
    }

    public static DateDateCalculator modifiedPrecedingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_PRECEDING);
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
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getDateCalculator(java.lang.String,
     *      java.lang.String)
     */
    public DateDateCalculator getDateCalculator(final String name, final String holidayHandlerType) {
        final DateDateCalculator cal = new DateDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        cal.setHolidayHandler(getHolidayHandler(holidayHandlerType));

        return cal;
    }

    public HolidayHandler<Date> getHolidayHandler(final String holidayHandlerType) {
        if (HolidayHandlerType.FORWARD.equals(holidayHandlerType)) {
            return new DateForwardHandler();
        } else if (BACKWARD.equals(holidayHandlerType)) {
            return new DateBackwardHandler();
        } else if (MODIFIED_FOLLOWING.equals(holidayHandlerType)) {
            return new DateModifiedFollowingHandler();
        } else if (MODIFIED_PRECEDING.equals(holidayHandlerType)) {
            return new DateModifiedPreceedingHandler();
        } else if (FORWARD_UNLESS_MOVING_BACK.equals(holidayHandlerType)) {
            return new DateForwardUnlessNegativeHandler();
        } else if (holidayHandlerType != null) {
            throw new IllegalArgumentException("Unsupported HolidayHandler: " + holidayHandlerType);
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see net.objectlab.kit.datecalc.jdk.JdkDateCalculatorFactory#getPeriodCountCalculator()
     */
    public PeriodCountCalculator<Date> getPeriodCountCalculator() {
        return PCC;
    }

    public IMMDateCalculator<Date> getIMMDateCalculator() {
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
