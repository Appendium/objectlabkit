package net.objectlab.kit.pf;

import java.util.List;

public interface ValidationResults {
    boolean isValid();

    List<RuleIssue> getIssues();

    List<ValidatedPortfolioLine> getLines();
}
