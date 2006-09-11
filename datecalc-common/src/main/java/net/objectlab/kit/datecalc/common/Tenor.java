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
 * Holds only valid unit and TenorCode: Day, Week, Month, Year, IMM, Overnight,
 * Spot.
 * 
 * @author Benoit Xhenseval
 * @author $LastChangedBy$
 * @version $Revision$ $Date$
 * 
 */
public class Tenor {
    private int units = 0;

    private TenorCode code;

    public Tenor(final int units, final TenorCode code) {
        this.units = units;
        this.code = code;
    }

    public int getUnits() {
        return units;
    }

    public TenorCode getCode() {
        return code;
    }

    public boolean hasUnits() {
        return code.acceptUnits();
    }

    @Override
    public String toString() {
        return (units != 0 ? "" + units : "") + code.getCode();
    }

    /**
     * @param tenor
     *            the tenor, e.g. 1D, 3W, SP, IMM etc
     * @exception IllegalArgumentException
     *                if the tenor is not a valid on
     */
    public static Tenor valueOf(final String tenor) {
        final StringBuffer unitsBuf = new StringBuffer();
        final StringBuffer codeBuf = new StringBuffer();
        boolean invalid = false;
        final int size = tenor.length();

        for (int i = 0; i < size && !invalid; i++) {
            final char c = tenor.charAt(i);

            if (c >= '0' && c <= '9') {
                if (codeBuf.length() != 0) {
                    throw new IllegalArgumentException("[" + tenor + "] is not a valid tenor");
                } else {
                    unitsBuf.append(c);
                }
            } else {
                codeBuf.append(c);
            }
        }

        int parsedUnits = 0;
        if (unitsBuf.length() > 0) {
            parsedUnits = Integer.parseInt(unitsBuf.toString());
        }
        final TenorCode parsedCode = TenorCode.fromCode(codeBuf.toString());
        if (parsedCode == null) {
            throw new IllegalArgumentException("[" + codeBuf + "] is not a valid TenorCode");
        }

        if (!parsedCode.acceptUnits() && unitsBuf.length() > 0) {
            throw new IllegalArgumentException("[" + codeBuf + "] does not accept units");
        }

        if (parsedCode.acceptUnits() && unitsBuf.length() == 0) {
            throw new IllegalArgumentException("[" + codeBuf + "] requires units");
        }

        return new Tenor(parsedUnits, parsedCode);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + units;
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
        final Tenor other = (Tenor) obj;
        if (code == null) {
            if (other.code != null) {
                return false;
            }
        } else if (!code.equals(other.code)) {
            return false;
        }
        if (units != other.units) {
            return false;
        }
        return true;
    }
}
