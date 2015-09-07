package net.objectlab.kit.pf;

public interface RuleIssue {
    Severity getSeverity();

    String getRule();

    String getMsg();

    ValidatedPortfolioLine getLine();
}
