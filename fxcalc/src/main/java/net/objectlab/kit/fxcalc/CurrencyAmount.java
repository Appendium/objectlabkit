package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;

/**
 * Read-only representation of a currency and a value.
 *
 * Hesitated creating this but it may be better to be explicit when doing conversions with the FX Rate.
 *
 * @author Benoit Xhenseval
 */
public interface CurrencyAmount {

    String getCurrency();

    BigDecimal getAmount();

    /**
     * Swap the sign on the amount and return a MonetaryAmount (implementation could make it immutable and return a new entity)
     */
    CurrencyAmount negate();

    /**
     * Add the amount with the existing one and return a MonetaryAmount (implementation could make it immutable and return a new entity)
     * @throws IllegalArgumentException if the money.currency does not match the current one.
     */
    CurrencyAmount add(CurrencyAmount money);

    /**
     * Subtract the amount from the existing one and return a MonetaryAmount (implementation could make it immutable and return a new entity)
     * @throws IllegalArgumentException if the money.currency does not match the current one.
     */
    CurrencyAmount subtract(CurrencyAmount money);
}
