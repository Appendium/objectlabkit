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
 * $Id: AbstractDateCalculator.java 309 2010-03-23 21:01:49Z marchy $
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
package net.objectlab.kit.util;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Benoit
 *
 */
public class WeightedAverage implements Serializable {
    private static final long serialVersionUID = 4687472725716492770L;
    private final Total total = new Total();
    private final Total totalExpanded = new Total();
    private int count = 0;
    private final boolean includeZeros;
    
    public WeightedAverage() {
    	this.includeZeros = true;
    }
    
    public WeightedAverage(final boolean includeZeros) {
    	this.includeZeros = includeZeros;
    }

    public BigDecimal getTotal() {
        return total.getTotal();
    }

    public void add(final BigDecimal value, final BigDecimal weightAsAValue) {
        if (includeZeros || BigDecimalUtil.isNotZero(value)) {
            count++;
            total.add(weightAsAValue);
            totalExpanded.add(BigDecimalUtil.multiply(value, weightAsAValue));
        }
    }

    public BigDecimal getWeightedAverage() {
        return BigDecimalUtil.divide(totalExpanded.getTotal(), total.getTotal(), BigDecimal.ROUND_HALF_UP);
    }

    public int getCount() {
        return count;
    }
}
