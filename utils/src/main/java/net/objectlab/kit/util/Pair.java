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

import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Pair<E1, E2> implements Serializable {

    private static final int MULTIPLIER = 31;
    private static final long serialVersionUID = 1L;
    private E1 element1;
    private E2 element2;

    public static <E1, E2> Pair<E1, E2> create(final E1 element1, final E2 element2) {
        return new Pair<E1, E2>(element1, element2);
    }

    public Pair(final E1 element1, final E2 element2) {
        this.element1 = element1;
        this.element2 = element2;
    }

    public Pair() {
    }

    public E1 getElement1() {
        return element1;
    }

    public E2 getElement2() {
        return element2;
    }

    public void setElement1(final E1 element1) {
        this.element1 = element1;
    }

    public void setElement2(final E2 element2) {
        this.element2 = element2;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = MULTIPLIER * result + ((element1 == null) ? 0 : element1.hashCode());
        result = MULTIPLIER * result + ((element2 == null) ? 0 : element2.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pair other = (Pair) obj;
        if (element1 == null) {
            if (other.element1 != null) {
                return false;
            }
        } else if (!element1.equals(other.element1)) {
            return false;
        }
        if (element2 == null) {
            if (other.element2 != null) {
                return false;
            }
        } else if (!element2.equals(other.element2)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new StandardToStringStyle());
    }
}
