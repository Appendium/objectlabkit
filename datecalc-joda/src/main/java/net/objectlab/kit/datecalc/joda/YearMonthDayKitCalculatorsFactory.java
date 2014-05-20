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
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.FORWARD;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_FOLLOWING;
import static net.objectlab.kit.datecalc.common.HolidayHandlerType.MODIFIED_PRECEDING;
import net.objectlab.kit.datecalc.common.AbstractKitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.DateCalculator;
import net.objectlab.kit.datecalc.common.HolidayHandlerType;
import net.objectlab.kit.datecalc.common.IMMDateCalculator;
import net.objectlab.kit.datecalc.common.KitCalculatorsFactory;
import net.objectlab.kit.datecalc.common.PeriodCountCalculator;

import org.joda.time.YearMonthDay;

/**
 * The default factory for getting Joda <code>YearMonthDay</code> based
 * calculators.
 * 
 * @author Benoit Xhenseval
 * 
 */
public class YearMonthDayKitCalculatorsFactory extends AbstractKitCalculatorsFactory<YearMonthDay> {

    private static final YearMonthDayKitCalculatorsFactory DEFAULT = new YearMonthDayKitCalculatorsFactory();

    private static final PeriodCountCalculator<YearMonthDay> PCC = new YearMonthDayPeriodCountCalculator();

    private static final IMMDateCalculator<YearMonthDay> IMMDC = new YearMonthDayIMMDateCalculator();

    public static YearMonthDayKitCalculatorsFactory getDefaultInstance() {
        return DEFAULT;
    }
    
    public static YearMonthDayDateCalculator forwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD);
    }
    
    public static YearMonthDayDateCalculator backwardCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.BACKWARD);
    }
    
    public static YearMonthDayDateCalculator forwardUnlessMovingBackCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.FORWARD_UNLESS_MOVING_BACK);
    }
    
    public static YearMonthDayDateCalculator modifiedFollowingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_FOLLOWING);
    }
    
    public static YearMonthDayDateCalculator modifiedPrecedingCalculator(final String name) {
        return DEFAULT.getDateCalculator(name, HolidayHandlerType.MODIFIED_PRECEDING);
    }

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
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
     * @throws IllegalArgumentException if name is null
     */
    public YearMonthDayDateCalculator getDateCalculator(final String name, final String holidayHandlerType) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null, use anything.");
        }
        final YearMonthDayDateCalculator cal = new YearMonthDayDateCalculator();
        cal.setName(name);
        setHolidays(name, cal);

        if (FORWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayForwardHandler());
        } else if (BACKWARD.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayBackwardHandler());
        } else if (MODIFIED_FOLLOWING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayModifiedFollowingHandler());
        } else if (MODIFIED_PRECEDING.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayModifiedPrecedingHandler());
        } else if (FORWARD_UNLESS_MOVING_BACK.equals(holidayHandlerType)) {
            cal.setHolidayHandler(new YearMonthDayForwardUnlessNegativeHandler());
        } else if (holidayHandlerType != null) {
            throw new IllegalArgumentException("Unsupported HolidayHandler: " + holidayHandlerType);
        }
        return cal;
    }

    public PeriodCountCalculator<YearMonthDay> getPeriodCountCalculator() {
        return PCC;
    }

    public IMMDateCalculator<YearMonthDay> getIMMDateCalculator() {
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
