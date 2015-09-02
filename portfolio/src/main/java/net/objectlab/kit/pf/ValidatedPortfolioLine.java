package net.objectlab.kit.pf;

import java.math.BigDecimal;

public interface ValidatedPortfolioLine extends ExistingPortfolioLine {
    BigDecimal getAllocationWeight();

    Validation getValidation();
}
