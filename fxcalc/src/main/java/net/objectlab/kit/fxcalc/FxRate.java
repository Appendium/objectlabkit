package net.objectlab.kit.fxcalc;

import java.math.BigDecimal;

public interface FxRate {
    String getFromCcy();

    String getToCcy();

    String getCrossCcy();

    BigDecimal getBid();

    BigDecimal getMid();

    BigDecimal getOffer();

    BigDecimal getBidInMarketConvention();

    BigDecimal getMidInMarketConvention();

    BigDecimal getOfferInMarketConvention();
}
