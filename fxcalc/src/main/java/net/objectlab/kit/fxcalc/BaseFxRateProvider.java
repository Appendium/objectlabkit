package net.objectlab.kit.fxcalc;

import java.util.Optional;

/**
 * If the user cannot provide a snapshot of the rates (base rates at least), or if it is important
 * to get the latest Base Rates, then provide an implementation of this interface to the calculator.
 *
 * Used in conjunction with the FxRateCalculatorBuilder.cacheBaseRates it is possible to request the base rates
 * (i.e. vs USD for instance) once or for every call.
 *
 * Note: It is NOT the role of ObjectLabKit to provide an implementation for this, you can alternatively use
 * FxRateCalculatorBuilder.addRateSnapshot or FxRateCalculatorBuilder.ratesSnapshot to give the base rates to the calculator.
 *
 * @author Benoit Xhenseval
 *
 */
@FunctionalInterface
public interface BaseFxRateProvider {
    /**
     * Up to the implementer to get the latest FX Rate for the given pair, that could be from your DB, from Reuters or
     * your own market making tools.
     * @param pair the currency pair (typically a base one vs USD or vs EUR)
     * @return optional FxRate
     */
    Optional<FxRate> getLatestRate(CurrencyPair pair);
}
