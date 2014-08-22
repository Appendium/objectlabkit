package net.objectlab.kit.fxcalc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FxRateCalculatorBuilder {
    private Map<CurrencyPair, FxRate> rates = new HashMap<>();

    public FxRateCalculatorBuilder rates(final Collection<FxRate> rates) {
        if (rates != null) {
            this.rates.clear();
            this.rates = rates.stream().collect(Collectors.toMap(t -> t.getCurrencyPair(), t -> t));
        }
        return this;
    }

    public FxRateCalculatorBuilder addRate(final FxRate rate) {
        if (rate != null) {
            this.rates.put(rate.getCurrencyPair(), rate);
        }
        return this;
    }

    public Map<? extends CurrencyPair, ? extends FxRate> getRates() {
        return rates;
    }
}
