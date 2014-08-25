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
 */package net.objectlab.kit.datecalc.common.ccy;

import java.util.Set;

import net.objectlab.kit.datecalc.common.WorkingWeek;

/**
 * According to http://www.londonfx.co.uk/valdates.html, some currencies should
 * take into account USD holidays for T+1 (whilst calculating the Spot Date).
 *
 * @author Benoit Xhenseval
 * @since 1.4.0
 */
public interface CurrencyCalculatorConfig {
    /**
     * @param crossCcy the cross currency used
     * @return set of currency code subject to CrossCcy Holidays for T+1. (for USD, typically MXN/CLP/ARS).
     */
    Set<String> getCurrenciesSubjectToCrossCcyForT1(String crossCcy);

    /**
     * Return a default Mon-Fri for most, but some might be Sun-Thu (Arabic countries).
     * @param currency
     * @return the WorkingWeek registered for this currency other the default Mon-Fri.
     */
    WorkingWeek getWorkingWeek(String currency);
}
