package net.objectlab.kit.fxcalc;

/**
 * Utility interface to determine the market convention for the FX Rate for a given currency pair.
 * See <a href="https://en.wikipedia.org/wiki/Currency_pair">https://en.wikipedia.org/wiki/Currency_pair</a>, Although there is no standards-setting body or ruling organization,
 * the established priority ranking of the major currencies is  Euro (EUR), Pound sterling (GBP), Australian dollar (AUD),
 * New Zealand dollar (NZD), United States dollar (USD), Canadian dollar (CAD), Swiss franc (CHF), Japanese yen (JPY).
 * @see StandardMajorCurrencyRanking for an implementation using this convention
 */
public interface MajorCurrencyRanking {
    /**
     * Given 2 currencies, return the major one if there is one, otherwise returns the first currency.
     */
    String selectMajorCurrency(String ccy1, String ccy2);

    /**
     * Given a CurrencyPair, return the major Currency if there is one, otherwise returns the first currency.
     */
    String selectMajorCurrency(CurrencyPair pair);

    /**
     * returns true if the ccy1 is the major one for the given currency pair.
     */
    boolean isMarketConvention(String ccy1, String ccy2);

    /**
     * returns true if the pair.ccy1 is the major one for the given currency pair.
     */
    boolean isMarketConvention(CurrencyPair pair);
}
