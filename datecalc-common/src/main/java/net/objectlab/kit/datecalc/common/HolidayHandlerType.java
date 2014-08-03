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
 * Define a series of standard way to handle holidays.
 *
 * @author Benoit Xhenseval
 *
 */
public final class HolidayHandlerType {

    /**
     * A Forward handler will move the date forward if it falls on a non working
     * day.
     */
    public static final String FORWARD = "forward";

    /**
     * A backward handler will move the date backward if it falls on a non
     * working day.
     */
    public static final String BACKWARD = "backward";

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * A modified following handler will move the date forward if it falls on a
     * non working day BUT, if the new date falls into another month, it will
     * revert to moving backward until it finds a working day.
     */
    public static final String MODIFIED_FOLLOWING = "modifiedFollowing";

    /**
     * A modified preceding handler will move the date backward if it falls on
     * a non working day BUT, if the new date falls into another month, it will
     * revert to moving forward until it finds a working day.
     */
    public static final String MODIFIED_PRECEDING = "modifiedPreceding";

    /**
     * A handler that moves the date forward unless the increment is negative
     * (eg moveByDays(-2)) in which case it behaves like a Backward handler.
     */
    public static final String FORWARD_UNLESS_MOVING_BACK = "forwardUnlessMovingBack";

    private HolidayHandlerType() {
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
