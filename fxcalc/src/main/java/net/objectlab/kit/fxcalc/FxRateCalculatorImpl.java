package net.objectlab.kit.fxcalc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FxRateCalculatorImpl implements FxRateCalculator {
    private final Map<CurrencyPair, FxRate> rates = new HashMap<>();

    public FxRateCalculatorImpl(final FxRateCalculatorBuilder builder) {
        rates.putAll(builder.getRates());
    }

    @Override
    public Optional<FxRate> findFx(final CurrencyPair ccyPair) {
        FxRate fxRate = rates.get(ccyPair);
        if (fxRate == null) {
            // try inverse
            final FxRate inverse = rates.get(ccyPair.createInverse());

            if (inverse != null) {
                fxRate = inverse.createInverse();
                rates.put(ccyPair, fxRate);
            } else {
                fxRate = findCrossCcy(ccyPair, "GBP");
            }
        }

        return Optional.ofNullable(fxRate);
    }

    private FxRate findCrossCcy(CurrencyPair ccyPair, String crossCcy) {
        CurrencyPair xCcyPair = CurrencyPair.of(crossCcy, ccyPair.getCcy1());
        FxRate xCcy1 = rates.get(xCcyPair);
        boolean xCcy1Inverse = false;
        if (xCcy1 == null) {
            // try inverse
            final FxRate inverse = rates.get(xCcyPair.createInverse());
            if (inverse != null) {
                xCcy1 = inverse.createInverse();
                xCcy1Inverse = true;
            }
        }

        if (xCcy1 != null) {
            CurrencyPair xCcy2Pair = CurrencyPair.of(ccyPair.getCcy2(), crossCcy);
            FxRate xCcy2 = rates.get(xCcy2Pair);
            boolean xCcy2Inverse = false;
            if (xCcy2 == null) {
                // try inverse
                final FxRate inverse = rates.get(xCcy2Pair.createInverse());
                if (inverse != null) {
                    xCcy2 = inverse.createInverse();
                    xCcy2Inverse = true;
                }
            }

            return CrossRateCalculator.calculateCross(ccyPair, xCcy1, xCcy2, 10, StandardMajorCurrencyRanking.getDefault());
        }
        return null;
    }

}
