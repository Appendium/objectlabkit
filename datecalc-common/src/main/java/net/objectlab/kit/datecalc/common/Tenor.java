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

import java.io.Serializable;

/**
 * Holds only valid unit and TenorCode: Day, Week, Month, Year, Overnight,
 * Spot.
 *
 * @author Benoit Xhenseval
 *
 */
public class Tenor implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int units;

    private final TenorCode code;

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
        return (units != 0 ? String.valueOf(units) : "") + code.getCode();
    }

    // -----------------------------------------------------------------------
    //
    // ObjectLab, world leaders in the design and development of bespoke
    // applications for the securities financing markets.
    // www.ObjectLab.co.uk
    //
    // -----------------------------------------------------------------------

    /**
     * @param tenor
     *            the tenor, e.g. 1D, 3W, SP etc
     * @exception IllegalArgumentException
     *                if the tenor is not a valid on
     */
    public static Tenor valueOf(final String tenor) {
        final StringBuilder unitsBuf = new StringBuilder();
        final StringBuilder codeBuf = new StringBuilder();
        final boolean invalid = false;
        final int size = tenor.length();

        parseCode(tenor, unitsBuf, codeBuf, invalid, size);

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

    private static void parseCode(final String tenor, final StringBuilder unitsBuf, final StringBuilder codeBuf, final boolean invalid,
            final int size) {
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
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (code == null ? 0 : code.hashCode());
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
        return units == other.units;
    }
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
