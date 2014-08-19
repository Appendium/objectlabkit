package net.objectlab.kit.fxcalc;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to determine the market convention for the FX Rate for a given currency pair.
 * See <a href="https://en.wikipedia.org/wiki/Currency_pair">https://en.wikipedia.org/wiki/Currency_pair</a>, Although there is no standards-setting body or ruling organization,
 * the established priority ranking of the major currencies is  Euro (EUR), Pound sterling (GBP), Australian dollar (AUD),
 * New Zealand dollar (NZD), United States dollar (USD), Canadian dollar (CAD), Swiss franc (CHF), Japanese yen (JPY).
 */
public final class StandardMajorCurrencyRanking implements MajorCurrencyRanking {
    private static final Map<String, Integer> RANKS = new HashMap<String, Integer>();
    private static final MajorCurrencyRanking DEFAULT = new StandardMajorCurrencyRanking();
    static {
        int i = 1;
        RANKS.put("EUR", i++);
        RANKS.put("GBP", i++);
        RANKS.put("AUD", i++);
        RANKS.put("NZD", i++);
        RANKS.put("USD", i++);
        RANKS.put("CAD", i++);
        RANKS.put("CHF", i++);
        RANKS.put("JPY", i++);
    }

    public static MajorCurrencyRanking getDefault() {
        return DEFAULT;
    }

    public StandardMajorCurrencyRanking() {
    }

    /**
     * Given 2 currencies, return the major one if there is one, otherwise returns the first currency.
     */
    @Override
    public String selectMajorCurrency(final String ccy1, final String ccy2) {
        return RANKS.getOrDefault(ccy1, 99).intValue() <= RANKS.getOrDefault(ccy2, 99).intValue() ? ccy1 : ccy2;
    }

    /**
     * returns true if the ccy1 is the major one for the given currency pair.
     */
    @Override
    public boolean isMarketConvention(final String ccy1, final String ccy2) {
        return selectMajorCurrency(ccy1, ccy2).equals(ccy1);
    }
}
