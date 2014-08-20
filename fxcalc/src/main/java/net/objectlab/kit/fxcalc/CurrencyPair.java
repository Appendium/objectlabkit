package net.objectlab.kit.fxcalc;

import java.util.Optional;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class CurrencyPair {
    private final String ccy1;
    private final String ccy2;

    public CurrencyPair(final String ccy1, final String ccy2) {
        super();
        this.ccy1 = ccy1;
        this.ccy2 = ccy2;
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
        return ccy1.equalsIgnoreCase(ccy) || ccy2.equalsIgnoreCase(ccy);
    }

    public Optional<String> findCommonCcy(final CurrencyPair otherPair) {
        if (containsCcy(otherPair.getCcy1())) {
            return Optional.of(ccy1);
        }
        return containsCcy(otherPair.getCcy2()) ? Optional.of(ccy2) : Optional.empty();
    }

    public CurrencyPair createInverse() {
        return new CurrencyPair(ccy2, ccy1);
    }

}
