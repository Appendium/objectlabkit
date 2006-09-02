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
package net.objectlab.kit.datecalc.common;

/**
 * TODO add javadoc
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 * @param <E>
 */
public interface PeriodCountCalculator<E> {

    static final int YEAR_360 = 360;

    static final int MONTHS_IN_YEAR = 12;

    static final double YEAR_365_0 = 365.0;

    static final double YEAR_360_0 = 360.0;

    static final int MONTH_31_DAYS = 31;

    static final int MONTH_30_DAYS = 30;

    int dayDiff(final E start, final E end, PeriodCountBasis basis);

    double monthDiff(final E start, final E end, PeriodCountBasis basis);

    double yearDiff(final E start, final E end, PeriodCountBasis basis);
}
