package net.objectlab.kit.pf;

import java.util.List;

public interface Validation {
    boolean hasValidationErrors();

    boolean hasValidationWarnings();

    List<RuleIssue> getIssues();
}
