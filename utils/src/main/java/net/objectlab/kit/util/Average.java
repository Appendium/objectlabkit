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
import java.util.Optional;

/**
 * @author Benoit
 *
 */
public final class Average implements Serializable {
    private static final long serialVersionUID = 4630559777899225672L;
    private Total sum = new Total(BigDecimal.ZERO, 8);
    private BigDecimal maximum;
    private BigDecimal minimum;
    private int count;

    public Average() {
    }

    public Average(final BigDecimal start) {
        sum = new Total(start);
        determineMinMax(start);
    }

    public Average(final int scale) {
        final BigDecimal bd = BigDecimal.ZERO;
        sum = new Total(bd.setScale(scale));
    }

    private Average determineMinMax(final BigDecimal value) {
        if (maximum == null || BigDecimalUtil.compareTo(value, maximum) == 1) {
            maximum = value;
        }
        if (minimum == null || BigDecimalUtil.compareTo(value, minimum) == -1) {
            minimum = value;
        }
        return this;
    }

    public Average add(final BigDecimal... values) {
        if (values != null) {
            for (BigDecimal val : values) {
                sum.add(val);
                determineMinMax(val);
                count++;
            }
        }
        return this;
    }

    public BigDecimal getTotal() {
        return sum.getTotal();
    }

    public int getDataPoints() {
        return count;
    }

    public BigDecimal getAverage() {
        return BigDecimalUtil.divide(getTotal(), new BigDecimal(count), BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public String toString() {
        return StringUtil.concatWithSpaces("Total:", getTotal(), "Points", getDataPoints(), "Avg:", getAverage());
    }

    public Optional<BigDecimal> getMaximum() {
        return Optional.ofNullable(maximum);
    }

    public Optional<BigDecimal> getMinimum() {
        return Optional.ofNullable(minimum);
    }
}
