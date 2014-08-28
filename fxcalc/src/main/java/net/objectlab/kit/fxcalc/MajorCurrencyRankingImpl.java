package net.objectlab.kit.fxcalc;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.objectlab.kit.util.StringUtil;

/**
 * Utility class to determine the market convention for the FX Rate for a given currency pair.
 * See <a href="https://en.wikipedia.org/wiki/Currency_pair">https://en.wikipedia.org/wiki/Currency_pair</a>, for a conventional list
 * but the user can provide the ordered list of major currencies.
 */
public class MajorCurrencyRankingImpl implements MajorCurrencyRanking {
    private static final int DEFAULT_UNRANKED_VALUE = 99;
    private final Map<String, Integer> ranks;

    /**
     * User can define their own ordered list of major currencies
     * @param orderedCurrencies must be ordered!
     */
    public MajorCurrencyRankingImpl(final List<String> orderedCurrencies) {
        if (orderedCurrencies == null) {
            ranks = Collections.emptyMap();
        } else {
            ranks = new HashMap<>(orderedCurrencies.size());
            int i = 1;
            for (final String s : orderedCurrencies) {
                ranks.put(StringUtil.toUpperCase(s), i++);
            }
        }
    }

    /**
     * Given 2 currencies, return the major one if there is one, otherwise returns the first currency.
     */
    @Override
    public String selectMajorCurrency(final String ccy1, final String ccy2) {
        return ranks.getOrDefault(StringUtil.toUpperCase(ccy1), DEFAULT_UNRANKED_VALUE).intValue() <= ranks.getOrDefault(StringUtil.toUpperCase(ccy2), DEFAULT_UNRANKED_VALUE).intValue() ? ccy1
                : ccy2;
    }

    /**
     * Given a CurrencyPair, return the major Currency if there is one, otherwise returns the first currency.
     */
    @Override
    public String selectMajorCurrency(final CurrencyPair pair) {
        return selectMajorCurrency(pair.getCcy1(), pair.getCcy2());
    }

    /**
     * returns true if the ccy1 is the major one for the given currency pair.
     */
    @Override
    public boolean isMarketConvention(final String ccy1, final String ccy2) {
        return selectMajorCurrency(ccy1, ccy2).equals(ccy1);
    }

    /**
     * returns true if the pair.ccy1 is the major one for the given currency pair.
     */
    @Override
    public boolean isMarketConvention(final CurrencyPair pair) {
        return isMarketConvention(pair.getCcy1(), pair.getCcy2());
    }
}
