package net.objectlab.kit.fxcalc;

import java.util.Optional;

public interface FxRateCalculator {
    /**
     * Find or calculate the FX Rate for the given pair.
     */
    Optional<FxRate> findFx(CurrencyPair ccyPair);
}
