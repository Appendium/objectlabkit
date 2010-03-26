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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author xhensevalb
 *
 */
public class Triplet<E1, E2, E3> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int HASH_INITIAL_VAL = 17;
    private static final int MULTIPLIER = 31;
    private E1 element1;
    private E2 element2;
    private E3 element3;

    public E1 getElement1() {
        return element1;
    }

    public static <E1, E2, E3> Triplet<E1, E2, E3> create(final E1 element1, final E2 element2, final E3 element3) {
        return new Triplet<E1, E2, E3>(element1, element2, element3);
    }

    public Triplet(final E1 element1, final E2 element2, final E3 element3) {
        super();
        this.element1 = element1;
        this.element2 = element2;
        this.element3 = element3;
    }

    public void setElement1(final E1 element1) {
        this.element1 = element1;
    }

    public E2 getElement2() {
        return element2;
    }

    public void setElement2(final E2 element2) {
        this.element2 = element2;
    }

    public E3 getElement3() {
        return element3;
    }

    public void setElement3(final E3 element3) {
        this.element3 = element3;
    }

    @Override
    public boolean equals(final Object rhs) {
        if (rhs == null) {
            return false;
        }
        if (!(rhs instanceof Triplet)) {
            return false;
        }
        final Triplet that = (Triplet) rhs;

        return new EqualsBuilder().append(element1, that.element1).append(element2, that.element2).append(element3, that.element3).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_INITIAL_VAL, MULTIPLIER).append(element1).append(element2).append(element3).toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, new StandardToStringStyle());
    }
}
