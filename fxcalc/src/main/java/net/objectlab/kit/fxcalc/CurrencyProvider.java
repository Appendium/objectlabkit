package net.objectlab.kit.fxcalc;

public interface CurrencyProvider {
    int getFractionDigits(String currencyCode);

    int getRounding(String currencyCode);
}
