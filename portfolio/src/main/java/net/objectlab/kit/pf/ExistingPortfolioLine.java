package net.objectlab.kit.pf;

import java.math.BigDecimal;

public interface ExistingPortfolioLine {
    String getAssetCode();

    String getAssetName();

    BigDecimal getQuantity();

    BigDecimal getPriceInPortfolioCcy();

    BigDecimal getValueInPortfolioCcy();
}
