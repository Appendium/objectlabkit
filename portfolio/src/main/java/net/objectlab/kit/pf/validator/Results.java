package net.objectlab.kit.pf.validator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.objectlab.kit.pf.ExistingPortfolio;
import net.objectlab.kit.pf.RuleIssue;
import net.objectlab.kit.pf.Severity;
import net.objectlab.kit.pf.ValidatedPortfolioLine;
import net.objectlab.kit.pf.ValidationResults;
import net.objectlab.kit.util.StringUtil;

public class Results implements ValidationResults {
    private final List<ValidatedPortfolioLine> lines = new ArrayList<>();

    private final List<RuleIssue> issues = new ArrayList<>();

    public Results(final ExistingPortfolio p) {
        p.getLines().forEach(t -> lines.add(new ValidatedPortfolioLineImpl(t, this)));
    }

    @Override
    public boolean isValid() {
        return issues.isEmpty();
    }

    @Override
    public List<RuleIssue> getIssues() {
        return issues;
    }

    public void addIssue(final Severity sev, final String ruleName, final String message, ValidatedPortfolioLineImpl line) {
        issues.add(new RuleIssueImpl(sev, ruleName, message, line));
    }

    @Override
    public List<ValidatedPortfolioLine> getLines() {
        return lines;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Code", 20), 20));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Asset name", 30), 31));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Quantity", 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Value", 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate("Weight", 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(" Validation Issues", 250), 12));
        b.append(StringUtil.NEW_LINE);

        lines.forEach(t -> b.append(t.toString()).append(StringUtil.NEW_LINE));

        return b.toString();
    }
}
