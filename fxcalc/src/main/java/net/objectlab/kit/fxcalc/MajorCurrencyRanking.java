package net.objectlab.kit.fxcalc;

/**
 * Utility interface to determine the market convention for the FX Rate for a given currency pair.
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
 * @see net.objectlab.kit.fxcalc.StandardMajorCurrencyRanking
 */
public interface MajorCurrencyRanking {
    /**
     * @return Given 2 currencies, return the major one if there is one, otherwise returns the first currency.
     */
    String selectMajorCurrency(String ccy1, String ccy2);

    /**
     * @return Given a CurrencyPair, return the major Currency if there is one, otherwise returns the first currency.
     */
    String selectMajorCurrency(CurrencyPair pair);

    /**
     * @return true if the ccy1 is the major one for the given currency pair.
     */
    boolean isMarketConvention(String ccy1, String ccy2);

    /**
     * @return true if the pair.ccy1 is the major one for the given currency pair.
     */
    boolean isMarketConvention(CurrencyPair pair);
}
