package net.objectlab.kit.fxcalc;

import java.util.Optional;

/**
 * The FX Rate calculator.  If the user request a Base rate (or its Inverse), it will provide the rate from the given Base Rate
 * snapshot or the BaseFxRateProvider.
 *
 * @author Benoit Xhenseval
 */
@FunctionalInterface
public interface FxRateCalculator {
    /**
     * Find or calculate the FX Rate for the given pair.
     * @return the FX rate if it exists or can be calculated
     */
    Optional<FxRate> findFx(CurrencyPair ccyPair);
}
