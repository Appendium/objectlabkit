package net.objectlab.kit.fxcalc;

import java.util.HashMap;
import java.util.Map;

/**
 * See https://en.wikipedia.org/wiki/Currency_pair, Although there is no standards-setting body or ruling organization,
 * the established priority ranking of the major currencies is  Euro, Pound sterling, Australian dollar, New Zealand dollar,
 * United States dollar, Canadian dollar, Swiss franc, Japanese yen.
 *
 */
public final class MajorCurrencyRanking {
    private static final Map<String, Integer> RANKS = new HashMap<String, Integer>();
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

    private MajorCurrencyRanking() {
    }

    /**
     * Given 2 currencies, return the major one if there is one, otherwise returns the first currency.
     */
    public static String findMajorCurrency(final String ccy1, final String ccy2) {
        return RANKS.getOrDefault(ccy1, 99).intValue() <= RANKS.getOrDefault(ccy2, 99).intValue() ? ccy1 : ccy2;
    }

    /**
     * returns true if the ccy1 is the major one.
     */
    public static boolean isMarketConvention(final String ccy1, final String ccy2) {
        return findMajorCurrency(ccy1, ccy2).equals(ccy1);
    }
}
