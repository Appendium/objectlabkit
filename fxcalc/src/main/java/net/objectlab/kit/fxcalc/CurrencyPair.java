package net.objectlab.kit.fxcalc;

import java.util.Optional;

import net.objectlab.kit.util.StringUtil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Immutable class representing a Currency pair, ccy1/ccy2; thread-safe and able to be used in Collections.
 * Note that the currencies will be converted to UPPER CASE.
 */
public class CurrencyPair {
    private final String ccy1;
    private final String ccy2;

    /**
     * @throws IllegalArgumentException if ccy1 or ccy2 are blank.
     */
    public CurrencyPair(final String ccy1, final String ccy2) {
        super();
        if (!StringUtil.noneBlank(ccy1, ccy2)) {
            throw new IllegalArgumentException("ccy1 and ccy2 cannot be blank");
        }
        this.ccy1 = StringUtil.toUpperCase(ccy1);
        this.ccy2 = StringUtil.toUpperCase(ccy2);
    }

    public static CurrencyPair of(final String ccy1, final String ccy2) {
        return new CurrencyPair(ccy1, ccy2);
    }

    public String getCcy1() {
        return ccy1;
    }

    public String getCcy2() {
        return ccy2;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(final Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return ccy1 + "." + ccy2;
    }

    public boolean containsCcy(final String ccy) {
        return ccy1.equalsIgnoreCase(StringUtil.toUpperCase(ccy)) || ccy2.equalsIgnoreCase(StringUtil.toUpperCase(ccy));
    }

    /**
     * Given another CurrencyPair find the common Currency (if any) or the first if both are identical
     * @param otherPair
     * @return the common ccy or Empty optional
     */
    public Optional<String> findCommonCcy(final CurrencyPair otherPair) {
        if (containsCcy(otherPair.getCcy1())) {
            return Optional.of(otherPair.getCcy1());
        }
        return containsCcy(otherPair.getCcy2()) ? Optional.of(otherPair.getCcy2()) : Optional.empty();
    }

    /**
     * Returns a new CurrencyPair ccy2 / ccy1 (useful for FxRate).
     */
    public CurrencyPair createInverse() {
        return new CurrencyPair(ccy2, ccy1);
    }

}
