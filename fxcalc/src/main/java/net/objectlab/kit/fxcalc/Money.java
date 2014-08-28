/**
 *
 */
package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;

import net.objectlab.kit.util.BigDecimalUtil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A simple class to represent an immutable amount of a given currency.
 * 
 * @author Benoit Xhenseval
 */
public class Money implements MonetaryAmount {
    private final String currency;
    private final BigDecimal amount;

    public Money(final String currency, final BigDecimal amount) {
        super();
        this.currency = currency;
        this.amount = amount;
    }

    public static Money of(final String currency, final BigDecimal amount) {
        return new Money(currency, amount);
    }

    public static Money of(final String currency, final String amount) {
        return new Money(currency, BigDecimalUtil.bd(amount));
    }

    public static Money of(final String currency, final long amount) {
        return new Money(currency, BigDecimal.valueOf(amount));
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return currency + " " + amount.toPlainString();
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
    public MonetaryAmount negate() {
        return new Money(currency, BigDecimalUtil.negate(amount));
    }

    @Override
    public MonetaryAmount add(final MonetaryAmount money) {
        if (!money.getCurrency().equals(currency)) {
            throw new IllegalArgumentException("You cannot add " + money.getCurrency() + " with " + currency);
        }
        return new Money(currency, BigDecimalUtil.add(amount, money.getAmount()));
    }

    @Override
    public MonetaryAmount subtract(final MonetaryAmount money) {
        if (!money.getCurrency().equals(currency)) {
            throw new IllegalArgumentException("You cannot add " + money.getCurrency() + " with " + currency);
        }
        return new Money(currency, BigDecimalUtil.subtract(amount, money.getAmount()));
    }

}
