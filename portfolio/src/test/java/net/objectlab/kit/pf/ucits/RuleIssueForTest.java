package net.objectlab.kit.pf.ucits;

import net.objectlab.kit.pf.RuleIssue;
import net.objectlab.kit.pf.Severity;
import net.objectlab.kit.pf.ValidatedPortfolioLine;

public class RuleIssueForTest implements RuleIssue {
    private Severity severity;
    private String rule;
    private String msg;
    private String lineAssetCode;
    private ValidatedPortfolioLine line;

    public RuleIssueForTest() {
    }

    public RuleIssueForTest(final RuleIssue t) {
        this.severity = t.getSeverity();
        this.rule = t.getRule();
        this.msg = t.getMsg();
        this.line = t.getLine();
        this.lineAssetCode = t.getLine().getAssetCode();
    }

    public String getLineAssetCode() {
        return lineAssetCode;
    }

    public void setLineAssetCode(String lineAssetCode) {
        this.lineAssetCode = lineAssetCode;
    }

    @Override
    public ValidatedPortfolioLine getLine() {
        return line;
    }

    public void setLine(final ValidatedPortfolioLine line) {
        this.line = line;
    }

    @Override
    public Severity getSeverity() {
        return severity;
    }

    public void setSeverity(final Severity severity) {
        this.severity = severity;
    }

    @Override
    public String getRule() {
        return rule;
    }

    public void setRule(final String rule) {
        this.rule = rule;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

}
