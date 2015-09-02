package net.objectlab.kit.pf.validator;

import java.util.ArrayList;
import java.util.List;

import net.objectlab.kit.pf.RuleIssue;
import net.objectlab.kit.pf.Severity;
import net.objectlab.kit.pf.Validation;
import net.objectlab.kit.util.StringUtil;

import org.apache.commons.lang.StringUtils;

public class ValidationImpl implements Validation {
    private final List<RuleIssue> issues = new ArrayList<>();

    @Override
    public String toString() {
        final StringBuilder b = new StringBuilder();
        boolean first = true;
        for (RuleIssue issue : issues) {
            if (!first) {
                b.append(StringUtil.NEW_LINE).append(StringUtils.repeat(" ", 81));
            }
            b.append(issue.toString());
            first = false;
        }
        return b.toString();
    }

    @Override
    public boolean hasValidationErrors() {
        return issues.stream().anyMatch(t -> t.getSeverity() == Severity.MANDATORY);
    }

    @Override
    public boolean hasValidationWarnings() {
        return issues.stream().anyMatch(t -> t.getSeverity() == Severity.WARNING);
    }

    @Override
    public List<RuleIssue> getIssues() {
        return issues;
    }

    public void addIssue(final Severity sev, final String ruleName, final String message) {
        issues.add(new RuleIssueImpl(sev, ruleName, message));
    }
}
