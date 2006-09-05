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
 * Some instruments require a period Bi Annually which then can be Mar-Sep or
 * Jun-Dec.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 */
public enum IMMPeriod {
    /** Bi-annually March and September */
    BI_ANNUALY_MAR_SEP,
    /** Bi-annually June and December */
    BI_ANNUALY_JUN_DEC,
    /** Conventional period: Quarterly: eg March, June, September, December */
    QUARTERLY,
    /** Annually, jump from 1y to the next */
    ANNUALLY
}
