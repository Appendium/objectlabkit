package net.objectlab.kit.pf;

import java.util.List;

public interface ValidationResults {
    Validation getPortfolioValidation();

    List<? extends ValidatedPortfolioLine> getLines();
}
