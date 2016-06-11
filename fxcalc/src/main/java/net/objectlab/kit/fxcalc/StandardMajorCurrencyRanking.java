package net.objectlab.kit.fxcalc;

import java.util.Arrays;

/**
 * Utility class to determine the market convention for the FX Rate for a given currency pair.
 * See <a href="https://en.wikipedia.org/wiki/Currency_pair">https://en.wikipedia.org/wiki/Currency_pair</a>, Although there is no standards-setting body or ruling organization,
 * the established priority ranking of the major currencies is:
 * <ol>
 * <li>Euro (EUR)</li>
 * <li>Pound sterling (GBP)</li>
 * <li>Australian dollar (AUD)</li>
 * <li>New Zealand dollar (NZD)</li>
 * <li>United States dollar (USD)</li>
 * <li>Canadian dollar (CAD)</li>
 * <li>Swiss franc (CHF)</li>
 * <li>Norvegian Krone (NOK)</li>
 * <li>Swedish Krone (SEK)</li>
 * <li>Japanese yen (JPY)</li>
 * </ol>
 */
public final class StandardMajorCurrencyRanking extends MajorCurrencyRankingImpl {
    private static final MajorCurrencyRanking DEFAULT = new StandardMajorCurrencyRanking();

    private StandardMajorCurrencyRanking() {
        super(Arrays.asList("EUR", "GBP", "AUD", "NZD", "USD", "CAD", "CHF", "NOK", "SEK", "JPY"));
    }

    public static MajorCurrencyRanking getDefault() {
        return DEFAULT;
    }

}
