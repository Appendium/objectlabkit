/**
 *
 */
package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;

import net.objectlab.kit.util.BigDecimalUtil;
import net.objectlab.kit.util.StringUtil;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A simple class to represent an immutable amount of a given currency.
 *
 * @author Benoit Xhenseval
 */
public class Cash implements CurrencyAmount {
    private final String currency;
    private final BigDecimal amount;

    public Cash(final String currency, final BigDecimal amount) {
        super();
        this.currency = StringUtil.toUpperCase(currency);
        this.amount = amount;
    }

    public static Cash of(final String currency, final BigDecimal amount) {
        return new Cash(currency, amount);
    }

    public static Cash of(final String currency, final String amount) {
        return new Cash(currency, BigDecimalUtil.bd(amount));
    }

    public static Cash of(final String currency, final long amount) {
        return new Cash(currency, BigDecimal.valueOf(amount));
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

    /**
     * Swap the sign on the amount and return a new immutable Money.
     */
    @Override
    public CurrencyAmount negate() {
        return new Cash(currency, BigDecimalUtil.negate(amount));
    }

    /**
     * Add the amount with the existing one and return a new immutable Money.
     * @throws IllegalArgumentException if the money.currency does not match the current one.
     */
    @Override
    public CurrencyAmount add(final CurrencyAmount money) {
        if (!money.getCurrency().equals(currency)) {
            throw new IllegalArgumentException("You cannot add " + money.getCurrency() + " with " + currency);
        }
        return new Cash(currency, BigDecimalUtil.add(amount, money.getAmount()));
    }

    /**
     * Subtract the amount from the existing one and return a new immutable Money.
     * @throws IllegalArgumentException if the money.currency does not match the current one.
     */
    @Override
    public CurrencyAmount subtract(final CurrencyAmount money) {
        if (!money.getCurrency().equals(currency)) {
            throw new IllegalArgumentException("You cannot add " + money.getCurrency() + " with " + currency);
        }
        return new Cash(currency, BigDecimalUtil.subtract(amount, money.getAmount()));
    }

}
