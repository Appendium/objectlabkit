package net.objectlab.kit.pf;

import java.math.BigDecimal;
import java.util.List;

public interface ExistingPortfolio {
    String getId();

    String getAffiliateCode();

    String getPartyCode();

    String getPortfolioCcy();

    BigDecimal getPortfolioValue();

    List<ExistingPortfolioLine> getLines();
}
