package net.objectlab.kit.fxcalc;

import java.util.Optional;

/**
 * The FX Rate calculator.  If the user request a Base rate (or its Inverse), it will provide the rate from the given Base Rate
 * snapshot or the BaseFxRateProvider.
 *
 * @author Benoit Xhenseval
 */
public interface FxRateCalculator {
    /**
     * Find or calculate the FX Rate for the given pair.
     */
    Optional<FxRate> findFx(CurrencyPair ccyPair);
}
