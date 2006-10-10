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
 * A series of Standard Tenors used by the financial industry.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public final class StandardTenor {
    private StandardTenor() {
    }

    public static final Tenor SPOT = new Tenor(0, TenorCode.SPOT);

    public static final Tenor OVERNIGHT = new Tenor(0, TenorCode.OVERNIGHT);

    public static final Tenor T_1D = new Tenor(1, TenorCode.DAY);

    public static final Tenor T_1W = new Tenor(1, TenorCode.WEEK);

    public static final Tenor T_1M = new Tenor(1, TenorCode.MONTH);

    public static final Tenor T_2M = new Tenor(2, TenorCode.MONTH);

    // -----------------------------------------------------------------------
    //
    //    ObjectLab, world leaders in the design and development of bespoke 
    //          applications for the securities financing markets.
    //                         www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    public static final Tenor T_3M = new Tenor(3, TenorCode.MONTH);

    public static final Tenor T_6M = new Tenor(6, TenorCode.MONTH);

    public static final Tenor T_9M = new Tenor(9, TenorCode.MONTH);

    public static final Tenor T_1Y = new Tenor(1, TenorCode.YEAR);

    public static final Tenor T_2Y = new Tenor(2, TenorCode.YEAR);

    public static final Tenor T_3Y = new Tenor(3, TenorCode.YEAR);

    public static final Tenor T_4Y = new Tenor(4, TenorCode.YEAR);

    public static final Tenor T_5Y = new Tenor(5, TenorCode.YEAR);

    public static final Tenor T_7Y = new Tenor(7, TenorCode.YEAR);

    public static final Tenor T_10Y = new Tenor(10, TenorCode.YEAR);

    public static final Tenor T_15Y = new Tenor(15, TenorCode.YEAR);

    public static final Tenor T_20Y = new Tenor(20, TenorCode.YEAR);

    public static final Tenor T_30Y = new Tenor(30, TenorCode.YEAR);

    public static final Tenor T_50Y = new Tenor(50, TenorCode.YEAR);
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
