package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Currency;

public class JdkCurrencyProvider implements CurrencyProvider {

    @Override
    public int getFractionDigits(String currencyCode) {
        try {
            return Currency.getInstance(currencyCode).getDefaultFractionDigits();
        } catch (IllegalArgumentException iae) {
            // do nothing
        }
        return 2;
    }

    @Override
    public int getRounding(String currencyCode) {
        return "JPY".equalsIgnoreCase(currencyCode) ? BigDecimal.ROUND_DOWN : BigDecimal.ROUND_HALF_UP;
    }
}
