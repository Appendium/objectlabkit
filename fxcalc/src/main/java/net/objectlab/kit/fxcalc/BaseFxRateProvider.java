package net.objectlab.kit.fxcalc;

import java.util.Optional;

public interface BaseFxRateProvider {
    Optional<FxRate> getLatetsRate(CurrencyPair pair);
}
