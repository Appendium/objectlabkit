package net.objectlab.kit.fxcalc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Parameters for an immutable FxRateCalculator.
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
        StringBuilder b = new StringBuilder();
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
    public FxRateCalculatorBuilder baseFxRateProvider(BaseFxRateProvider baseFxRateProvider) {
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
     */
    public FxRateCalculatorBuilder cacheResults(boolean cacheResults) {
        this.cacheResults = cacheResults;
        return this;
    }

    /**
     * If using the baseFxRateProvider, should the rate be cached in the calculator, defaulted to true.
     */
    public FxRateCalculatorBuilder cacheBaseRates(boolean cacheBaseRates) {
        this.cacheBaseRates = cacheBaseRates;
        return this;
    }

    /**
     * Number of decimal places to use on a rate, defaulted to 6.
     */
    public FxRateCalculatorBuilder precisionForFxRate(int precisionForFxRate) {
        this.precisionForFxRate = precisionForFxRate;
        return this;
    }

    /**
     * Number of decimal places to use for a calculation involving an inversed rate, defaulted to 12
     */
    public FxRateCalculatorBuilder precisionForInverseFxRate(int precisionForInverseFxRate) {
        this.precisionForInverseFxRate = precisionForInverseFxRate;
        return this;
    }

    /**
     * If the rate required from the calculator is not immediately available, use a cross currency to calculate it; this 
     * gives you the opportunity to select which currencies to use in which order. Defaulted to USD and then EUR.
     */
    public FxRateCalculatorBuilder orderedCurrenciesForCross(List<String> orderedCurrenciesForCross) {
        if (orderedCurrenciesForCross != null) {
            this.orderedCurrenciesForCross = orderedCurrenciesForCross;
        }
        return this;
    }

    /**
     * The interface to determine which currency is major, defaults to StandardMajorCurrencyRanking.
     */
    public FxRateCalculatorBuilder majorCurrencyRanking(MajorCurrencyRanking majorCurrencyRanking) {
        if (majorCurrencyRanking != null) {
            this.majorCurrencyRanking = majorCurrencyRanking;
        }
        return this;
    }

    /**
     * Snapshot of FxRate, typically they are the Base FX Rates (e.g. vs USD)
     */
    public FxRateCalculatorBuilder ratesSnapshot(final Collection<FxRate> rates) {
        if (rates != null) {
            this.ratesSnapshot.clear();
            this.ratesSnapshot = rates.stream().collect(Collectors.toMap(t -> t.getCurrencyPair(), t -> t));
        }
        return this;
    }

    /**
     * Snapshot of FxRate, typically they are the Base FX Rates (e.g. vs USD)
     */
    public FxRateCalculatorBuilder addRateSnapshot(final FxRate rate) {
        if (rate != null) {
            this.ratesSnapshot.put(rate.getCurrencyPair(), rate);
        }
        return this;
    }

    public Map<? extends CurrencyPair, ? extends FxRate> getRatesSnapshot() {
        return ratesSnapshot;
    }
}
