package net.objectlab.kit.fxcalc;

/**
 * Utility interface to determine the market convention for the FX Rate for a given currency pair.
 */
public interface MajorCurrencyRanking {
    String selectMajorCurrency(String ccy1, String ccy2);

    boolean isMarketConvention(String ccy1, String ccy2);
}
