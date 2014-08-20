package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;
import java.util.Optional;

public interface FxRate {
    CurrencyPair getCurrencyPair();

    Optional<String> getCrossCcy();

    boolean isMarketConvention();

    BigDecimal getBid();

    BigDecimal getMid();

    BigDecimal getAsk();

    BigDecimal getBidInMarketConvention();

    BigDecimal getMidInMarketConvention();

    BigDecimal getAskInMarketConvention();

    FxRate createInverse();

    String getDescription();
}
