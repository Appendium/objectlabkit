package net.objectlab.kit.fxcalc;

import java.util.Arrays;

/**
 * Utility class to determine the market convention for the FX Rate for a given currency pair.
 * See <a href="https://en.wikipedia.org/wiki/Currency_pair">https://en.wikipedia.org/wiki/Currency_pair</a>, Although there is no standards-setting body or ruling organization,
 * the established priority ranking of the major currencies is  Euro (EUR), Pound sterling (GBP), Australian dollar (AUD),
 * New Zealand dollar (NZD), United States dollar (USD), Canadian dollar (CAD), Swiss franc (CHF), Japanese yen (JPY).
 */
public final class StandardMajorCurrencyRanking extends MajorCurrencyRankingImpl {
    private static final MajorCurrencyRanking DEFAULT = new StandardMajorCurrencyRanking();

    public static MajorCurrencyRanking getDefault() {
        return DEFAULT;
    }

    private StandardMajorCurrencyRanking() {
        super(Arrays.asList("EUR", "GBP", "AUD", "NZD", "USD", "CAD", "CHF", "JPY"));
    }
}
