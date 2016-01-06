package net.objectlab.kit.pf.validator;

import net.objectlab.kit.pf.RuleIssue;
import net.objectlab.kit.pf.Severity;
import net.objectlab.kit.pf.ValidatedPortfolioLine;

import org.apache.commons.lang.StringUtils;

public class RuleIssueImpl implements RuleIssue {
    private final Severity severity;
    private final String rule;
    private final String msg;
    private final ValidatedPortfolioLine line;

    public RuleIssueImpl(final Severity severity, final String rule, final String msg, ValidatedPortfolioLine line) {
        super();
        this.severity = severity;
        this.rule = rule;
        this.msg = msg;
        this.line = line;
    }

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        b.append(StringUtils.leftPad(StringUtils.abbreviate(getSeverity().toString(), 10), 11));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(getRule(), 30), 31));
        b.append(StringUtils.leftPad(StringUtils.abbreviate(getMsg(), 40), 41));
        return b.toString();
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    @Override
    public String getRule() {
        return rule;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public ValidatedPortfolioLine getLine() {
        return line;
    }

}
