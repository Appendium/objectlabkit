package net.objectlab.kit.pf;

import java.util.List;

public interface Validation {
    boolean isValid();

    List<RuleIssue> getIssues();
}
