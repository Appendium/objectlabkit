package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Parameters for an immutable FxRateCalculator.
 * The FX Calculator requires Base Rates to return rates, inverse rates or cross rates.  There are 3 ways to import rates via the FxRateCalculatorBuilder.
      <ol>
      <li>addRateSnapshot(FxRate rate): add the rate to an internal map per CurrencyPair, an immutable map will be given to the Calculator so the bases rates will NOT change.</li>
      <li>ratesSnapshot(Collection&lt;FxRate&gt; rates): add a collection of rates to an internal map per CurrencyPair, an immutable map will be given to the Calculator so the bases rates will NOT change.</li>
      <li>baseFxRateProvider(BaseFxRateProvider) You then control when the Base Rates change, the Calculator will call the required CurrencyPair you either every time or once (if cacheBaseRates is true).</li>
      </ol>
 * <pre>
 * final FxRateCalculatorBuilder builder = new FxRateCalculatorBuilder() //
        .addRateSnapshot(new FxRateImpl(CurrencyPair.of("EUR", "USD"), null, true, BigDecimalUtil.bd("1.6"), BigDecimalUtil.bd("1.61")))//
        .addRateSnapshot(new FxRateImpl(CurrencyPair.of("GBP", "CHF"), null, true, BigDecimalUtil.bd("2.1702"), BigDecimalUtil.bd("2.1707")))//
        .addRateSnapshot(new FxRateImpl(CurrencyPair.of("EUR", "GBP"), null, true, BigDecimalUtil.bd("0.7374"), BigDecimalUtil.bd("0.7379")))//
        .orderedCurrenciesForCross(Lists.newArrayList("GBP", "USD")) // Use GBP as the first cross ccy, if that doesn't work use USD
        .majorCurrencyRanking(StandardMajorCurrencyRanking.getdefault())
        .precisionForFxRate(6)
        .precisionForInverseFxRate(12)
        .currencyProvider(new JdkCurrencyProvider()) // use the JDK currency
        .cacheResults(true) // only calculate a cross Fx once, cache for subsequent requests
        .cacheBaseRates(true); // if a BaseFxRateRateProvider is used, cache the rates instead of calling again for same currency pair
 * </pre>
 * @author Benoit Xhenseval
 */
public class FxRateCalculatorBuilder {
    /**
     * Snapshot of FxRate, typically they are the Base FX Rates (e.g. vs USD)
     */
    private Map<CurrencyPair, FxRate> ratesSnapshot = new HashMap<>();
    /**
     * If it is important for you to get the latest rates every time, you can provide a rate provider using this interface.
     */
    private BaseFxRateProvider baseFxRateProvider;
    /**
     * The interface to determine which currency is major, defaults to StandardMajorCurrencyRanking.
     */
    private MajorCurrencyRanking majorCurrencyRanking = StandardMajorCurrencyRanking.getDefault();
    /**
     * If the rate required from the calculator is not immediately available, use a cross currency to calculate it; this
     * gives you the opportunity to select which currencies to use in which order. Defaulted to USD and then EUR.
     */
    private List<String> orderedCurrenciesForCross = new ArrayList<>();
    /**
     * Number of decimal places to use on a rate, defaulted to 6.
     */
    private int precisionForFxRate = 6;
    /**
     * Number of decimal places to use for a calculation involving an inversed rate, defaulted to 12
     */
    private int precisionForInverseFxRate = 12;
    /**
     * Once a new rate has been calculated, should it be cached for any further request, defaulted to true.
     */
    private boolean cacheResults = true;
    /**
     * If using the baseFxRateProvider, should the rate be cached in the calculator, defaulted to true.
     */
    private boolean cacheBaseRates = true;
    /**
     * The Rounding to use for BID, default HALF_DOWN
     */
    private int bidRounding = BigDecimal.ROUND_HALF_DOWN;
    /**
     * The Rounding to use for ASK, default HALF_UP
     */
    private int askRounding = BigDecimal.ROUND_HALF_UP;
    /**
     * CurrencyProvider the provider for currency details
     */
    private CurrencyProvider currencyProvider = new JdkCurrencyProvider();

    public FxRateCalculatorBuilder() {
        orderedCurrenciesForCross.add("USD");
        orderedCurrenciesForCross.add("EUR");
    }

    /**
     * Check if the builder is valid i.e.
     * * precisions greater than 1
     * * ratesSnapshot not empty or a BaseFxRateProvider was provided
     * @throws IllegalArgumentException if the validation fails.
     */
    public void checkValid() {
        final StringBuilder b = new StringBuilder();
        if (precisionForFxRate < 2) {
            b.append("Precision for FX should be >=2");
        }
        if (precisionForInverseFxRate < 2) {
            if (b.length() > 0) {
                b.append(",");
            }
            b.append("Precision for 1/FX should be >=2");
        }
        if (ratesSnapshot.isEmpty() && baseFxRateProvider == null) {
            if (b.length() > 0) {
                b.append(",");
            }
            b.append("You must provide Base Rates Snapshots OR a BaseFxRateProvider");
        }

        if (b.length() > 0) {
            throw new IllegalArgumentException(b.toString());
        }
    }

    /**
     * If it is important for you to get the latest rates every time, you can provide a rate provider using this interface.
     */
    public FxRateCalculatorBuilder baseFxRateProvider(final BaseFxRateProvider baseFxRateProvider) {
        this.baseFxRateProvider = baseFxRateProvider;
        return this;
    }

    public MajorCurrencyRanking getMajorCurrencyRanking() {
        return majorCurrencyRanking;
    }

    public List<String> getOrderedCurrenciesForCross() {
        return Collections.unmodifiableList(orderedCurrenciesForCross);
    }

    public int getPrecisionForFxRate() {
        return precisionForFxRate;
    }

    public int getPrecisionForInverseFxRate() {
        return precisionForInverseFxRate;
    }

    public boolean isCacheResults() {
        return cacheResults;
    }

    public boolean isCacheBaseRates() {
        return cacheBaseRates;
    }

    public BaseFxRateProvider getBaseFxRateProvider() {
        return baseFxRateProvider;
    }

    /**
     * Once a new rate has been calculated, should it be cached for any further request?
     * @return the builder
     */
    public FxRateCalculatorBuilder cacheResults(final boolean cacheResults) {
        this.cacheResults = cacheResults;
        return this;
    }

    /**
     * If using the baseFxRateProvider, should the rate be cached in the calculator, defaulted to true.
     * @return the builder
     */
    public FxRateCalculatorBuilder cacheBaseRates(final boolean cacheBaseRates) {
        this.cacheBaseRates = cacheBaseRates;
        return this;
    }

    /**
     * Number of decimal places to use on a rate, defaulted to 6.
     * @return the builder
     */
    public FxRateCalculatorBuilder precisionForFxRate(final int precisionForFxRate) {
        this.precisionForFxRate = precisionForFxRate;
        return this;
    }

    /**
     * Number of decimal places to use for a calculation involving an inversed rate, defaulted to 12
     * @return the builder
     */
    public FxRateCalculatorBuilder precisionForInverseFxRate(final int precisionForInverseFxRate) {
        this.precisionForInverseFxRate = precisionForInverseFxRate;
        return this;
    }

    /**
     * Rounding for BID calculations.
     * @return the builder
     */
    public FxRateCalculatorBuilder bidRounding(final int bidRounding) {
        this.bidRounding = bidRounding;
        return this;
    }

    /**
     * Rounding for BID calculations.
     * @return the builder
     */
    public FxRateCalculatorBuilder askRounding(final int askRounding) {
        this.askRounding = askRounding;
        return this;
    }

    /**
     * If the rate required from the calculator is not immediately available, use a cross currency to calculate it; this
     * gives you the opportunity to select which currencies to use in which order. Defaulted to USD and then EUR.
     * @return the builder
     */
    public FxRateCalculatorBuilder orderedCurrenciesForCross(final List<String> orderedCurrenciesForCross) {
        if (orderedCurrenciesForCross != null) {
            this.orderedCurrenciesForCross = orderedCurrenciesForCross;
        }
        return this;
    }

    /**
     * The interface to determine which currency is major, defaults to StandardMajorCurrencyRanking.
     * @return the builder
     */
    public FxRateCalculatorBuilder majorCurrencyRanking(final MajorCurrencyRanking majorCurrencyRanking) {
        if (majorCurrencyRanking != null) {
            this.majorCurrencyRanking = majorCurrencyRanking;
        }
        return this;
    }

    /**
     * The interface to determine Currency details.
     * @return the builder
     */
    public FxRateCalculatorBuilder currencyProvider(final CurrencyProvider currencyProvider) {
        if (currencyProvider != null) {
            this.currencyProvider = currencyProvider;
        }
        return this;
    }

    /**
     * Snapshot of FxRate, typically they are the Base FX Rates (e.g. vs USD)
     * @return the builder
     */
    public FxRateCalculatorBuilder ratesSnapshot(final Collection<FxRate> rates) {
        if (rates != null) {
            this.ratesSnapshot.clear();
            this.ratesSnapshot = rates.stream().collect(Collectors.toMap(FxRate::getCurrencyPair, t -> t));
        }
        return this;
    }

    /**
     * Snapshot of FxRate, typically they are the Base FX Rates (e.g. vs USD).
     * @return the builder
     */
    public FxRateCalculatorBuilder addRateSnapshot(final FxRate rate) {
        if (rate != null) {
            this.ratesSnapshot.put(rate.getCurrencyPair(), rate);
        }
        return this;
    }

    public Map<CurrencyPair, FxRate> getRatesSnapshot() {
        return ratesSnapshot;
    }

    public int getBidRounding() {
        return bidRounding;
    }

    public int getAskRounding() {
        return askRounding;
    }

    public CurrencyProvider getCurrencyProvider() {
        return currencyProvider;
    }
}
