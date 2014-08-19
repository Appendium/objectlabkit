package net.objectlab.kit.fxcalc;

import java.util.Optional;

public interface FxRateCalculator {
    /**
     * Return an FX which would return a rate 'X' (for 1 fromCcy you would get X toCcy)
     */
    Optional<FxRate> findFx(String fromCcy, String toCcy);
}
