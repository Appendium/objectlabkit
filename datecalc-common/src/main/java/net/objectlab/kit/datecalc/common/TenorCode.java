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

public enum TenorCode {
    OVERNIGHT("ON", false),
    // TOMNEXT("TN", false),
    SPOT("SP", false),
    // SPOTNEXT("SN", false),
    // SPOTWEEK("SW", false),
    DAY("D", true), WEEK("W", true), MONTH("M", true), YEAR("Y", true);

    private final String code;

    private final boolean acceptUnits;

    private TenorCode(final String code, final boolean acceptUnits) {
        this.code = code;
        this.acceptUnits = acceptUnits;
    }

    public String getCode() {
        return code;
    }

    public static TenorCode fromCode(final String code) {
        for (final TenorCode ct : TenorCode.values()) {
            if (ct.getCode().equals(code)) {
                return ct;
            }
        }
        return null;
    }

    /**
     * @return true if the TenorCode can have units e.g. 1 Day, 3 Week but not 5
     *         IMM or 6 SP
     */
    public boolean acceptUnits() {
        return acceptUnits;
    }
}
